package com.example.finalproject.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.repo.MealRepo
import kotlinx.coroutines.launch

class RetrofitViewModel(private val mealRepo: MealRepo):ViewModel() {
    private val _meal = MutableLiveData<Meal>()
    private val _mealCollection=MutableLiveData<List<Meal>>()
    val meal: LiveData<Meal> = _meal
    val mealCollection=_mealCollection
    fun fetchRandom(){
        viewModelScope.launch {
            _meal.postValue(mealRepo.getRandom().meals[0])
        }
    }
    fun fetchRandomCollection(){
        viewModelScope.launch {
            _mealCollection.postValue(mealRepo.getRandom().meals)
        }
    }
}