package com.nop.google.gson.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yangzijun on 17-2-27.
 */
public class DataSet {
    public String album_id;
    public String album_title;
    @SerializedName("images")
    public List<AlbumImages> album_images;
}
