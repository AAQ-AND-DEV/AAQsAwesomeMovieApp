package com.aaqanddev.aaqsawesomeandroidapp.Utilities.converters;

import android.arch.persistence.room.TypeConverter;

import com.aaqanddev.aaqsawesomeandroidapp.Utilities.DataConverter;
import com.aaqanddev.aaqsawesomeandroidapp.pojo.SpokenLanguage;

import java.util.List;

public class SpokenLangConverter extends DataConverter<SpokenLanguage> {

    public SpokenLangConverter(){}

    public SpokenLangConverter(Class<SpokenLanguage> spokenLanguageClass) {
        super(spokenLanguageClass);
    }

    @TypeConverter
    @Override
    public String fromDataList(List<SpokenLanguage> values) {
        return super.fromDataList(values);
    }

    @TypeConverter
    @Override
    public List<SpokenLanguage> toTypeList(String valuesString) {
        return super.toTypeList(valuesString);
    }
}
