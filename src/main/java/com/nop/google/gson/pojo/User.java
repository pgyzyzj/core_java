package com.nop.google.gson.pojo;

/**
 * Created by yangzijun on 17-2-28.
 */
public class User {
    String name;
    Float weight;

    public static User instance(){
        User user=new User();
        user.name="pg";
        user.weight=Float.POSITIVE_INFINITY;
        return user;
    }
}
