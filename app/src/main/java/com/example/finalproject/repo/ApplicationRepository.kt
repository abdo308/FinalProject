package com.example.finalproject.repo

import com.example.finalproject.User

interface ApplicationRepository {

    suspend fun getUserByEmail(email: String) : User?
    suspend fun insertNewUser(user: User)

}