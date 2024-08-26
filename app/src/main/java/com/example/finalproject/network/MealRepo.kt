package com.example.finalproject.network

class MealRepo {
    suspend fun getRandom():Meal{
        return APIClient.getRandomProduct()
    }
}