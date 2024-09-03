package com.example.finalproject.db

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.models.User
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.Meal
import com.example.finalproject.repo.FavouritesRepo
import com.example.finalproject.repo.MealRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val repo: FavouritesRepo
) : ViewModel() {

    private val _favouritesList = MutableLiveData<MutableList<Meal>>()
    val favourites = _favouritesList

    fun getFavList(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _favouritesList.postValue(repo.getFavouritesList(email).favouriteMeals)
        }
    }

    fun addUser(userFavourites: UserFavourites) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertUser(userFavourites)
        }
    }

    fun updateFavList(email: String, favs: MutableList<Meal>) {
        viewModelScope.launch(Dispatchers.IO) {
            _favouritesList.postValue(favs)
            repo.updateFavouritesList(email, favs)
        }
    }
}