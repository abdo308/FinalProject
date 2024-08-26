package com.example.finalproject.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.repo.MealRepo

class ViewModelFactory(private val mealRepo: MealRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RetrofitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RetrofitViewModel(mealRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}