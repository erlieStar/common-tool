package com.javashitang.tool.domain;

import com.javashitang.tool.change.ColumnName;
import com.javashitang.tool.change.IgnoreColumn;
import lombok.Data;

@Data
public class Person {

    @ColumnName("名字")
    private String name;

    @ColumnName("年龄")
    private Integer age;

    @IgnoreColumn
    @ColumnName("地址")
    private String location;
}
