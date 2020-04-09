package com.javashitang.tool.page;

import com.javashitang.tool.OperStatus;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageHelper {

    public static final Pattern invalidSql = Pattern.compile("(^ *[^(select )].*)|(.* (limit|offset) .*)", Pattern.CASE_INSENSITIVE);

    private static final Logger logger = LoggerFactory.getLogger(PageHelper.class);

    protected static ThreadLocal<PageInfo> pageInfoThreadLocal = new ThreadLocal<>();

    public static boolean canBuildCountSql(String originSql) {
        Matcher m = PageHelper.invalidSql.matcher(originSql);
        return !m.find();
    }

    public static void preparePageRequest(int start, int size, int maxCount) {
        start = start > 0 ? start : 1;
        size = size > maxCount ? maxCount: size;
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurPage(start);
        pageInfo.setPageSize(size);
        pageInfoThreadLocal.set(pageInfo);
    }

    public static void preparePageRequest(int start, int size) {
        preparePageRequest(start, size, 500);
    }

    public static PageInfo popAfterAll() {
        PageInfo pageInfo = pageInfoThreadLocal.get();
        pageInfoThreadLocal.remove();
        return PageInfo.build(pageInfo.getCurPage(), pageInfo.getPageSize(), pageInfo.getTotalItem());
    }

    public static OperStatus clearAfterAll(OperStatus operStatus) {
        PageInfo pageInfo = pageInfoThreadLocal.get();
        pageInfoThreadLocal.remove();
        if (operStatus != null && pageInfo != null) {
            operStatus.setPageInfo(pageInfo.getCurPage(), pageInfo.getPageSize(), pageInfo.getTotalItem());
        }
        return operStatus;
    }

    /**
     * 构建查询总数的sql
     */
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
            logger.error("page helper failed parser sql: {}", originSql, e);
        }
        return inputSql;
    }
}
