package com.example.finalproject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    private val email : String,
    private var password : String,
    @ColumnInfo(name = "first_name")
    private val firstName : String,
    @ColumnInfo(name = "last_name")
    private val lastName : String
)