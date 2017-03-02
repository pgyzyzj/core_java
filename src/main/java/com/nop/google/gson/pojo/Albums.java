package com.nop.google.gson.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yangzijun on 17-2-27.
 */
public class Albums {
    public String title;
    public String message;
    public String[] errors = new String[]{};
    public String total;
    public int total_pages;
    public int page;
    public String limit;
    @SerializedName("dataset")
    public List<DataSet> dataSet;
}
