package com.aaqanddev.aaqsawesomeandroidapp.Utilities;

import android.arch.persistence.room.TypeConverter;

import com.aaqanddev.aaqsawesomeandroidapp.Interfaces.DataConverterInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//TODO oK -- so this is what I think I can do
//extend this class a bunch of times, just calling super.()
public abstract class DataConverter<T> implements Serializable {
    private final Gson gson;

    public DataConverter(){this.gson = new Gson();}

    public DataConverter(Class<T> tClass){
        this.gson = new Gson();
    }

    @TypeConverter
    public String fromDataList(List<T> values){

        if (values == null){
            return (null);
        }
        Type type = new TypeToken<List<T>>(){}.getType();
        String json = gson.toJson(values, type);
        return json;

    }

    @TypeConverter
    public List<T> toTypeList(String valuesString) {
        if (valuesString == null){
            return null;
        } else {
            Type type = new TypeToken<List<T>>(){}.getType();

            return new Gson().fromJson(valuesString, type);

        }
        //DTMS? I think I need to parse this? but
        //that seems ridiculous...

    }


}
