package com.nop.google.gson.adapter;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;

/**
 * Created by yangzijun on 17-2-24.
 */
public class DirectUseTypeAdapter {

    private String type;
    private Rgb rgb;

    static class Rgb {
        int red;
        int green;
        int blue;

        protected Rgb(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("red", red).add("green", green).add("blue", blue).toString();
        }
    }

    public static void main(String[] args) {
        String json = "{'rgb':'1,2,3'}";
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Rgb.class, new TypeAdapter<Rgb>() {
            public void write(JsonWriter out, Rgb value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.beginObject();
                out.name("rgb").value(Joiner.on(",").join(value.red, value.green, value.blue).toString());
                out.endObject();
            }

            public Rgb read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                String a = reader.nextString();
                Splitter splitter = Splitter.on(",");
                List<String> list = Lists.newArrayList(splitter.split(a));
                return new Rgb(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)));
            }
        });
        Gson gson = gb.create();
        DirectUseTypeAdapter type = gson.fromJson(json, DirectUseTypeAdapter.class);
        Rgb rgb=gson.fromJson(json,Rgb.class);
        System.out.println(rgb);
/*        Rgb rgb1 = new Rgb(111, 211, 33);
        System.out.println(gson.toJson(rgb1));*/


        //System.out.println(rgb);
    }
}
