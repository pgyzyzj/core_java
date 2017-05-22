package com.nop.google.guawa.collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by yangzijun on 17-4-19.
 */
public class ListsDemo {
    public static void main(String[] args) {
        List<Integer> lst= Lists.newArrayList(1,2,3);
        List<Integer> lstFilter= Lists.newArrayList(Collections2.filter(null, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input>5;
            }
        }));

        System.out.println(lstFilter.size());
    }
}
