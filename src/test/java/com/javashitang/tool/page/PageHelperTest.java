package com.javashitang.tool.page;

import com.javashitang.tool.OperStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PageHelperTest {

    @Test
    public void buildPageCountSql() {
        String sql = PageHelper.buildPageCountSql("select name from student where gender = 1");
        System.out.println(sql);

        sql = PageHelper.buildPageCountSql("select name from student where gender = 1 group by age");
        System.out.println(sql);
    }


    @Test
    public void popAfterAll() {
        // PageHelper是一个分页插件，相比于 Mybatis-PageHelper（https://github.com/pagehelper/Mybatis-PageHelper）
        // 的区别为，分页信息和数据信息没有耦合到一块
        int pageIndex = 1;
        int pageSize = 10;

        // 1.执行如下语句
        PageHelper.preparePageRequest(pageIndex, pageSize);
        // 2.从mapper中查询数据，sql中不用写limit，这里直接new一个，不取了
        List<Integer> list = new ArrayList<>();
        // 3.取出分页信息，设置后返回
        PageInfo pageInfo = PageHelper.popAfterAll();
        OperStatus result = OperStatus.newSuccess();
        result.setData(list);
        result.setPage(pageInfo);
    }
}
