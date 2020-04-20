package com.javashitang.tool.change;

import lombok.Data;

@Data
public class ChangeInfo {

    private String columnName;
    private String oldValue;
    private String newValue;
}
