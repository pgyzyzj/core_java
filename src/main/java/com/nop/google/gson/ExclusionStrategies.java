package com.nop.google.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.nop.google.gson.pojo.UserDate;

import java.util.Date;

/**
 * Created by yangzijun on 17-2-28.
 * 枚举类可以有自己的方法，枚举项相当于类的一个实例，实例则可以调用实例方法，参照ElusionStrategies枚举类的实现，这个应该非常有用
 */
public class ExclusionStrategies {

    public static void main(String[] args) {
        GsonBuilder gb=new GsonBuilder();
        gb.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> incomingClass) {
                return incomingClass== Date.class||incomingClass==boolean.class||incomingClass==Boolean.class;
            }
        });

        System.out.println(gb.create().toJson(UserDate.instance()));
    }
}
