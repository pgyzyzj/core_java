package com.nop.google.gson;

import com.google.common.collect.Lists;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nop.google.gson.pojo.AlbumImages;
import com.nop.google.gson.pojo.Albums;
import com.nop.google.gson.pojo.DataSet;

import java.lang.reflect.Field;

/**
 * Created by yangzijun on 17-2-27.
 */
public class Java2Json {

    public static void main(String[] args) {
        Albums albums = new Albums();
        albums.title = "Free Music Archive - Albums";
        albums.message = "";
        albums.total = "11259";
        albums.total_pages = 2252;
        albums.page = 1;
        albums.limit = "5";
        DataSet dataSet = new DataSet();
        dataSet.album_id = "7596";
        dataSet.album_title = "Album 1";
        AlbumImages image = new AlbumImages();
        image.image_id = "1";
        image.user_id = null;
        dataSet.album_images = Lists.newArrayList(image);
        albums.dataSet = Lists.newArrayList(dataSet);


        GsonBuilder builder = new GsonBuilder();
        //注意：名称转换策略没有使用注释的高
        builder.setFieldNamingStrategy(new FieldNamingStrategy() {
            @Override
            public String translateName(Field f) {
                if (f.getName().equals("albumId"))
                    return "field_naming_strategy";
                else
                    return f.getName();
            }
        });
        Gson gson = builder.setPrettyPrinting().serializeNulls().create();
        System.out.println(gson.toJson(albums));

    }
}
