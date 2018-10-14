package com.aaqanddev.aaqsawesomeandroidapp.Utilities.converters;

import android.arch.persistence.room.TypeConverter;

import com.aaqanddev.aaqsawesomeandroidapp.Utilities.DataConverter;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.Genre;

import java.util.List;

public class GenreConverter extends DataConverter<Genre> {
    public GenreConverter(){}
    public GenreConverter(Class<Genre> genreClass) {
        super(genreClass);
    }

    @TypeConverter
    @Override
    public String fromDataList(List<Genre> values) {
        return super.fromDataList(values);
    }

    @TypeConverter
    @Override
    public List<Genre> toTypeList(String valuesString) {
        return super.toTypeList(valuesString);
    }
    //do I need to specifically override the methods? I don't think so.
}
