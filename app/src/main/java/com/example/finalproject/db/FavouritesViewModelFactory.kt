package com.example.finalproject.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.network.RetrofitViewModel
import com.example.finalproject.repo.FavouritesRepo

class FavouritesViewModelFactory(private val favouritesRepo : FavouritesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouritesViewModel(favouritesRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}