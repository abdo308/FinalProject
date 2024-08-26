package com.example.finalproject.repo

import com.example.finalproject.models.User
import com.example.finalproject.network.Meal

interface ApplicationRepository {

    suspend fun getUserByEmail(email: String) : User?
    suspend fun insertNewUser(user: User)
    suspend fun getMealBySearch(search: String): List<Meal>
}