package com.nop.google.gson;

import com.google.gson.GsonBuilder;
import com.nop.google.gson.pojo.User;

/**
 * Created by yangzijun on 17-2-28.
 */
public class FloatPointSerialize {
    public static void main(String[] args) {
        User user=User.instance();
        GsonBuilder gb=new GsonBuilder();
        gb.serializeSpecialFloatingPointValues();
        System.out.println(gb.create().toJson(user));

    }
}
