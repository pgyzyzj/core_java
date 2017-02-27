package com.nop.google.guawa.collection;

import com.google.common.collect.*;

/**
 * Created by yangzijun on 17-2-22.
 */
public class MultiMaps {

    public static void multiMap() {
        Multimap<Integer, String> multiMap = HashMultimap.create();
        multiMap.put(1, "a");
        multiMap.put(2, "b");
        multiMap.put(3, "c");
        multiMap.put(1, "a2");
        System.out.println(multiMap.get(1));
    }

    public static void table(){
        Table<Integer, String, String> table = HashBasedTable.create();
        table.put(1, "a", "1a");
        table.put(1, "b", "1b");
        table.put(2, "a", "2a");
        table.put(2, "b", "2b");
        System.out.println(table.row(1).get("a"));
        Table transponedTable = Tables.transpose(table);
        System.out.println(transponedTable.toString());
    }

    public static void main(String[] args) {
        multiMap();
        table();
    }
}
