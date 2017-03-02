package com.nop.google.gson.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.nop.google.gson.pojo.DataSet;

import java.io.IOException;

/**
 * Created by yangzijun on 17-2-28.
 */
public class DataSetTypeAdapter extends TypeAdapter<DataSet>{
    @Override
    public DataSet read(JsonReader reader) throws IOException {
        // the first token is the start object
        JsonToken token = reader.peek();
        DataSet dataset = new DataSet();
        if (token.equals(JsonToken.BEGIN_OBJECT)) {
            reader.beginObject();
            while (!reader.peek().equals(JsonToken.END_OBJECT)) {
                if (reader.peek().equals(JsonToken.NAME)) {
                    if (reader.nextName().equals("album_url"))
                        dataset.album_title=reader.nextString();
                    else
                        reader.skipValue();

                }
            }
            reader.endObject();

        }
        return dataset;
    }

    @Override
    public void write(JsonWriter out, DataSet value) throws IOException {

    }
}
