package com.example.finalproject.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val email : String,
    val password : String,
    @ColumnInfo(name = "first_name")
    val firstName : String,
    @ColumnInfo(name = "last_name")
    val lastName : String
)