package com.bravo.domain.model.converters

import androidx.room.TypeConverter
import com.bravo.domain.model.ContactInfoProject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ContactInfoConverter {

    @TypeConverter
    fun listToJsonString(value: List<ContactInfoProject>?) = value?.let { Gson().toJson(value) }

    @TypeConverter
    fun jsonStringToList(value: String?): List<ContactInfoProject>? {
        val listType = object : TypeToken<List<ContactInfoProject>>() {}.type
        return value?.let { Gson().fromJson(value, listType) }
    }

}