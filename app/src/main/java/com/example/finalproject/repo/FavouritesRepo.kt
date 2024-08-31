package com.example.finalproject.repo

import com.example.finalproject.models.User
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.Meal

interface FavouritesRepo {
    suspend fun getFavouritesList(email : String) : UserFavourites
    suspend fun insertUser(userFavourites: UserFavourites)
    suspend fun updateFavouritesList(email : String,favouritesList : MutableList<Meal>)
}