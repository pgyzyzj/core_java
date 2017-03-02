package com.nop.google.gson.pojo;

import java.util.Date;

/**
 * Created by yangzijun on 17-2-28.
 */
public class UserDate {
    private String _name;
    private String email;
    private Boolean isDeveloper;
    private int age;
    private Date registerDate = new Date();

    public static UserDate instance() {
        UserDate userDate = new UserDate();
        userDate._name="testName";
        userDate.email="xx@gmail.com";
        userDate.isDeveloper=true;
        userDate.age=26;
        return userDate;
    }
}
