package com.example.finalproject.repo

import com.example.finalproject.models.User
import com.example.finalproject.network.Meal
import com.example.finalproject.network.RandomProduct
import retrofit2.Response

interface MealRepo {
    suspend fun getRandom(): RandomProduct
    suspend fun getMealBySearch(search: String): Response<RandomProduct>
}