package com.example.finalproject.db

import androidx.room.TypeConverter
import com.example.finalproject.network.Meal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealConverter {
    val type = object : TypeToken<List<Meal>>() {}.type
    @TypeConverter
    fun fromMealList(meals : List<Meal>) : String{
        return Gson().toJson(meals,type)
    }

    @TypeConverter
    fun toMealList(mealsString : String) : List<Meal>{
        return Gson().fromJson(mealsString,type)
    }
}