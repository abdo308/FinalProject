package com.example.finalproject.network

interface MealRepo {
    suspend fun getRandom():RandomProduct
}