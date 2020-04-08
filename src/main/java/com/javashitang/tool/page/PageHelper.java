package com.javashitang.tool.page;

import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.lang.Object;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageHelper {

    public static final String PAGE_INDEX = "pageIndex";
    public static final String PAGE_SIZE = "pageSize";

    public static final Pattern invalidSql = Pattern.compile("(^ *[^(select )].*)|(.* (limit|offset) .*)", Pattern.CASE_INSENSITIVE);

    private static final Logger logger = LoggerFactory.getLogger(PageHelper.class);

    private static ThreadLocal<PageInfo> pageInfo = new ThreadLocal<>();

    public static boolean canBuildCountSql(String originSql) {
        Matcher m = PageHelper.invalidSql.matcher(originSql);
        return !m.find();
    }

    @Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
    public static class MybatisPagePlugin implements Interceptor {

        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            PageInfo pageInfo = PageHelper.pageInfo.get();
            if (pageInfo == null || pageInfo.getMaxLife() <= 0) {
                // 执行原始的方法
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
                PageHelper.pageInfo.remove();
                return invocation.proceed();
            }
            String pageSql = this.buildPageSql(sql, pageInfo);
            SystemMetaObject.forObject(boundSql).setValue("sql", pageSql);

            // 将执行权交给下一个插件
            try {
                return invocation.proceed();
            } catch (Exception e) {
                logger.error("error while processed by pageHelper plugin", e);
                throw e;
            } finally {
                // 避免内存溢出
                PageHelper.pageInfo.remove();
            }
        }

        @Override
        public Object plugin(Object o) {
            return Plugin.wrap(o, this);
        }

        @Override
        public void setProperties(Properties properties) {

        }


        private String buildPageSql(String originSql, PageInfo pageInfo) {
            return new StringBuilder().append(originSql).append(" limit ")
                    .append(pageInfo.getPageSize() * (pageInfo.getNextPage() - 1))
                    .append(" , ").append(pageInfo.getPageSize()).toString();
        }


        private int queryItemCount(String sql, Connection connection, StatementHandler statementHandler,
                                   BoundSql boundSql) {
            String countSql = buildPageCountSql(sql);
            if (countSql == null) {
                return -1;
            }
            Object parameterObject = boundSql.getParameterObject();
            return 0;
        }

    }


    public static String buildPageCountSql(final String originSql) {
        CCJSqlParser sqlParser = new CCJSqlParser(new StringReader(originSql));
        String inputSql = null;
        try {
            SelectBody selectBody = sqlParser.SelectBody();
            ((PlainSelect) selectBody).setOrderByElements(null);
            SelectExpressionItem countExpression = new SelectExpressionItem();
            boolean hasGroupBy = ((PlainSelect) selectBody).getGroupByColumnReferences() != null;
            Column column = new Column();
            if (hasGroupBy) {
                column.setColumnName("1");
            } else {
                column.setColumnName("COUNT(*) as GTestXCountH");
            }
            countExpression.setExpression(column);
            List<SelectItem> countItem = new ArrayList<>(1);
            countItem.add(countExpression);
            ((PlainSelect) selectBody).setSelectItems(countItem);
            inputSql = selectBody.toString();
            if (hasGroupBy) {
                inputSql = String.format("SELECT COUNT(*) AS GTestXCountH FROM （%s） TMP", inputSql);
            }
        } catch (Exception e) {
            logger.error("failed parser sql: {}", originSql, e);
        }
        return inputSql;
    }
}
