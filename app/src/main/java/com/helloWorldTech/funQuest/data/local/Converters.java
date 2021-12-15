package com.helloWorldTech.funQuest.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    @TypeConverter // note this annotation
    public String fromAppDataEntityAgeRange(List<AppDataResponse.AgeRange> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<AppDataResponse.AgeRange>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<AppDataResponse.AgeRange> toAppDataEntityAgeRange(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<AppDataResponse.AgeRange>>() {
        }.getType();
        List<AppDataResponse.AgeRange> typesList = gson.fromJson(optionValuesString, type);
        return typesList;
    }

}
