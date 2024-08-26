package com.example.finalproject.repo

import com.example.finalproject.models.User

interface ApplicationRepository {

    suspend fun getUserByEmail(email: String) : User?
    suspend fun insertNewUser(user: User)

}