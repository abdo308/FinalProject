package com.example.finalproject.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RetrofitViewModel:ViewModel() {
    private   val m=MealRepo()

    fun getRandomProduct(){
        viewModelScope.launch(Dispatchers.IO) {
             val m=MealRepo()
            Log.d("msg",m.getRandom().idMeal)
        }
    }
}