package com.example.finalproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    private val email : String,
    private var password : String,
    private val firstName : String,
    private val secondName : String
)