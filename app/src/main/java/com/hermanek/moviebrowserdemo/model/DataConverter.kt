package com.hermanek.moviebrowserdemo.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.math.BigInteger


/**
 * Created by jhermanek on 02.03.2022.
 */

class DataConverter {
    @TypeConverter
    fun fromSpokenLanguageList(countryLang: List<SpokenLanguage?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<SpokenLanguage?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toSpokenLanguageList(value: String?): List<SpokenLanguage>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<SpokenLanguage?>?>() {}.type
        return gson.fromJson<List<SpokenLanguage>>(value, type)
    }

    @TypeConverter
    fun fromGenreList(countryLang: List<Genre?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toGenreList(value: String?): List<Genre>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre?>?>() {}.type
        return gson.fromJson<List<Genre>>(value, type)
    }

    @TypeConverter
    fun fromIntList(countryLang: List<Int?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toIntList(value: String?): List<Int>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.fromJson<List<Int>>(value, type)
    }

    @TypeConverter
    fun fromBigInteger(countryLang: BigInteger?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<BigInteger?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toBigInteger(value: String?): BigInteger? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<BigInteger?>() {}.type
        return gson.fromJson<BigInteger>(value, type)
    }

    @TypeConverter
    fun fromBelongsToCollection(countryLang: BelongsToCollection?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<BelongsToCollection?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toBelongsToCollection(value: String?): BelongsToCollection? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<BelongsToCollection?>() {}.type
        return gson.fromJson<BelongsToCollection>(value, type)
    }

    @TypeConverter
    fun fromProductionCompanyList(countryLang: List<ProductionCompany?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductionCompany?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toProductionCompanyList(value: String?): List<ProductionCompany>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductionCompany?>?>() {}.type
        return gson.fromJson<List<ProductionCompany>>(value, type)
    }

    @TypeConverter
    fun fromProductionCountryList(countryLang: List<ProductionCountry?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductionCountry?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toProductionCountryList(value: String?): List<ProductionCountry>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductionCountry?>?>() {}.type
        return gson.fromJson<List<ProductionCountry>>(value, type)
    }
}