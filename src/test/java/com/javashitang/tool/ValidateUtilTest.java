package com.javashitang.tool;

import com.javashitang.tool.pojo.Student;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ValidateUtilTest {

    @Test
    public void validate() {
        Student student = new Student();
        String message = ValidateUtil.validate(student);
        assertNotNull(message);
        student.setName("a");
        student.setAge(17);
        message = ValidateUtil.validate(student);
        assertNull(message);
    }
}
