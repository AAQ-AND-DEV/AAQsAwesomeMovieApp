package com.aaqanddev.aaqsawesomeandroidapp.Interfaces;

import android.arch.persistence.room.TypeConverter;

import java.lang.reflect.Type;
import java.util.List;

public interface DataConverterInterface<T> {

    String fromDataList(List<T> values);
    List<T> toTypeList(String values);
}
