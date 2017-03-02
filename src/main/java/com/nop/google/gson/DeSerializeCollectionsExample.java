package com.nop.google.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nop.google.gson.pojo.DataSet;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created by yangzijun on 17-2-28.
 */
public class DeSerializeCollectionsExample {
    public static void main(String[] args) {
        String json = "[{album_id:1,album_title:'album1'},{album_id:2,album_title:'album2'}]";

        Gson gson = new Gson();
        // create the type for the collection. In this case define that the collection is of type Dataset
        //方法一
        //Type datasetListType = new TypeToken<Collection<DataSet>>() {}.getType();
        //List<DataSet> datasets = gson.fromJson(json, datasetListType);
        //方法二
        DataSet[] datasets = gson.fromJson(json, DataSet[].class);
        for (DataSet dataset : datasets) {
            System.out.println(dataset.album_title);
            System.out.println(dataset.album_id);
        }
        // Prints
        //album1
        //1
        //album2
        //2

    }
}
