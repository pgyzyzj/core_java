package com.nop.google.gson;

import com.google.gson.Gson;

/**
 * Created by yangzijun on 17-2-28.
 */
public class ArrayExample {
    public static void main(String[] args) {
        Gson gson = new Gson();
        int[] ints = {1, 2, 3, 4, 5};
        String[] strings = {"abc", "def", "ghi"};
        // Serialization
        gson.toJson(ints);     // ==> [1,2,3,4,5]
        gson.toJson(strings);  // ==> ["abc", "def", "ghi"]
        // Deserialization
        int[] ints2 = gson.fromJson("[1,2,3,4,5]", int[].class);
        // ==> ints2 will be same as ints
    }
}
