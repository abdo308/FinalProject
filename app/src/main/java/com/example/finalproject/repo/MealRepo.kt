package com.example.finalproject.repo

import com.example.finalproject.network.Meal
import com.example.finalproject.network.RandomProduct

interface MealRepo {
    suspend fun getRandom(): RandomProduct
    suspend fun getMealBySearch(search: String): RandomProduct
}