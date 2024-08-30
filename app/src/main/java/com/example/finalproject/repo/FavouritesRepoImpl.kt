package com.example.finalproject.repo

import com.example.finalproject.db.LocalDataSource
import com.example.finalproject.models.User
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.Meal

class FavouritesRepoImpl(
    private val localDataSource: LocalDataSource
) : FavouritesRepo {
    override suspend fun getFavouritesList(email: String) : UserFavourites {
        return localDataSource.getDataUser(email)
    }

    override suspend fun insertUser(userFavourites: UserFavourites) {
        localDataSource.addUser(userFavourites)
    }

    override suspend fun updateFavouritesList(email: String, favouritesList: MutableList<Meal>) {
        localDataSource.updateData(email,favouritesList)
    }
}