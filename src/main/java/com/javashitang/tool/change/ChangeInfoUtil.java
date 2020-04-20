package com.javashitang.tool.change;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChangeInfoUtil {

    public static List<ChangeInfo> getChangeInfo(Object oldObj, Object newObj) {
        List<ChangeInfo> list = new ArrayList<>();
        Class<?> clazz = oldObj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            IgnoreColumn ignoreColumn = field.getAnnotation(IgnoreColumn.class);
            if (ignoreColumn != null) {
                continue;
            }
            try {
                Object oldValue = field.get(oldObj);
                Object newValue = field.get(newObj);
                boolean flag = Objects.deepEquals(oldValue, newValue);
                if (!flag) {
                    ColumnName columnName = field.getAnnotation(ColumnName.class);
                    ChangeInfo changeInfo = new ChangeInfo();
                    changeInfo.setColumnName(columnName.value());
                    changeInfo.setOldValue(String.valueOf(oldValue));
                    changeInfo.setNewValue(String.valueOf(newValue));
                    list.add(changeInfo);
                }
            } catch (Exception e) {

            }
        }
        return list;
    }
}
