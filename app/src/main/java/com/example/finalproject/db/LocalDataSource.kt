package com.example.finalproject.db

import com.example.finalproject.User

interface LocalDataSource {
    suspend fun getUserByEmail(email: String) : User?
    suspend fun insertNewUser(user: User)
}