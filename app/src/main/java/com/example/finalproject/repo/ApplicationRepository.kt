package com.example.finalproject.repo

import com.example.finalproject.models.User
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.Meal

interface ApplicationRepository {

    suspend fun getUserByEmail(email: String) : User?
    suspend fun insertNewUser(user: User)
    suspend fun getDataUser(email:String?): UserFavourites
    suspend fun addUser(userFavourite: UserFavourites)
    suspend fun updateData(email: String,list:MutableList<Meal>)

}