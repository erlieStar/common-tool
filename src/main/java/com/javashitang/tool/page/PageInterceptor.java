package com.javashitang.tool.page;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(PageHelper.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        PageInfo pageInfo = PageHelper.pageInfoThreadLocal.get();
        if (pageInfo == null) {
            // 执行原始的方法
            logger.debug("page helper skip");
            return invocation.proceed();
        }
        MetaObject metaStatementHandler = SystemMetaObject.forObject(invocation.getTarget());
        // 目标类可能被多个插件拦截，从而形成多次代理，通过下面的循环分离出原始类
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h.target");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        StatementHandler statementHandler = (StatementHandler) metaStatementHandler.getOriginalObject();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();

        // 不满足查询数量的sql
        if (!PageHelper.canBuildCountSql(sql)) {
            PageHelper.pageInfoThreadLocal.remove();
            return invocation.proceed();
        }
        String pageSql = this.buildPageSql(sql, pageInfo);
        // 重写分页sql
        SystemMetaObject.forObject(boundSql).setValue("sql", pageSql);
        Connection connection = (Connection) invocation.getArgs()[0];
        int count = this.queryItemCount(sql, connection, statementHandler, boundSql);
        pageInfo.setTotalItem(count);
        pageInfo.setTotalPage(pageInfo.getPageSize(), pageInfo.getTotalItem());
        // 将执行权交给下一个插件
        try {
            return invocation.proceed();
        } catch (Exception e) {
            logger.error("error while processed by pageHelper plugin", e);
            PageHelper.popAfterAll();
            throw e;
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


    /**
     * 构建分页的sql
     */
    private String buildPageSql(String originSql, PageInfo pageInfo) {
        return new StringBuilder(250).append(originSql).append(" limit ")
                .append(pageInfo.getPageSize() * (pageInfo.getCurPage() - 1))
                .append(" , ").append(pageInfo.getPageSize()).toString();
    }


    /**
     * 查询总数
     */
    private int queryItemCount(String sql, Connection connection, StatementHandler statementHandler,
                               BoundSql boundSql) {
        String countSql = PageHelper.buildPageCountSql(sql);
        if (countSql == null) {
            return -1;
        }
        try (PreparedStatement countStmt = connection.prepareStatement(countSql)) {
            statementHandler.getParameterHandler().setParameters(countStmt);
            int totalCount = 0;
            try (ResultSet rs = countStmt.executeQuery()) {
                if (rs.next()) {
                    totalCount = rs.getInt(1);
                }
                return totalCount;
            } catch (SQLException e) {
                logger.error("page helper failed execute sql", sql);
            }
        } catch (SQLException e) {
            logger.error("page helper failed get countStmt");
        }
        logger.error("page helper failed get sql count: {}", sql);
        return 0;
    }

}
