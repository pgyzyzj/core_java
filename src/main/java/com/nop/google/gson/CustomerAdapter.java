package com.nop.google.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nop.google.gson.adapter.DataSetTypeAdapter;
import com.nop.google.gson.pojo.Albums;
import com.nop.google.gson.pojo.DataSet;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yangzijun on 17-2-28.
 */
public class CustomerAdapter {
    public static void main(String[] args) throws MalformedURLException, IOException {
        String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";
        URL uri = new URL(url);
        URLConnection conn = uri.openConnection();
        //conn.setRequestProperty("User-Agent", "Mozilla/31.0 (compatible; MSIE 10.0; Windows NT; DigExt)");
        conn.setRequestProperty("User-agent","Mozilla/4.0");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Accept-Language", "zh-cn");
        BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer stringBuffer=new StringBuffer();
        String s=null;
        while((s=reader.readLine())!=null){
            stringBuffer.append(s);
        }
        String json=stringBuffer.toString();
        // Create the custom type adapter and register it with the GsonBuilder
        // class.
        Gson gson = new GsonBuilder().registerTypeAdapter(DataSet.class, new DataSetTypeAdapter()).create();
        // deserialize the json to Albums class. The Dataset objects are part of
        // the Albums class. Whenever Gson encounters an object of type DataSet
        // it calls the DatasetTypeAdapter to read and write json.
        Albums albums = gson.fromJson(json, Albums.class);
        System.out.println(albums.dataSet.get(0).album_title);
        // prints
        // http://freemusicarchive.org/music/The_Yes_Sirs/Through_The_Cracks_Mix_Vol_1/
    }
}
