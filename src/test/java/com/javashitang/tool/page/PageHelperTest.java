package com.javashitang.tool.page;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class PageHelperTest {

    @Test
    public void buildPageCountSql() {
        String sql = PageHelper.buildPageCountSql("select name from student where gender = 1");
        System.out.println(sql);

        sql = PageHelper.buildPageCountSql("select name from student where gender = 1 group by age");
        System.out.println(sql);
    }
}
