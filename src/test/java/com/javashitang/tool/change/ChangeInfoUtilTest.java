package com.javashitang.tool.change;

import com.javashitang.tool.domain.Person;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ChangeInfoUtilTest {

    @Test
    public void getChangeInfo() {

        Person oldPerson = new Person();
        oldPerson.setName("aa");
        oldPerson.setLocation("aaa");
        oldPerson.setAge(10);

        Person newPerson = new Person();
        newPerson.setName("bb");
        newPerson.setLocation("bbb");
        newPerson.setAge(20);

        List<ChangeInfo> changeInfoList = ChangeInfoUtil.getChangeInfo(oldPerson, newPerson);
        System.out.println(changeInfoList);
    }
}