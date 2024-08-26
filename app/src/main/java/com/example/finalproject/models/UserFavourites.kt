package com.example.finalproject.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.finalproject.db.MealConverter
import com.example.finalproject.network.Meal

@Entity(tableName = "users_favourites")
data class UserFavourites(
    @PrimaryKey
    val email:String,
    @ColumnInfo(name = "favourites")
    @TypeConverters(MealConverter::class)
    val favouriteMeals : List<Meal>
)
