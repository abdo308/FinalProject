package com.example.finalproject.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.repo.MealRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

fun getRandomCharacter(chars: String): Char {
    val randomIndex = Random.nextInt(chars.length)
    return chars[randomIndex]
}
class RetrofitViewModel(private val mealRepo: MealRepo):ViewModel() {
    private val _meal = MutableLiveData<Meal>()
    private val _mealCollection=MutableLiveData<List<Meal>>()
    val meal: LiveData<Meal> = _meal
    val mealCollection:LiveData<List<Meal>> = _mealCollection
    fun fetchRandom(){
        viewModelScope.launch(Dispatchers.IO) {
            _meal.postValue(mealRepo.getRandom().meals[0])
        }
    }
    fun fetchRandomCollection(){

        viewModelScope.launch(Dispatchers.IO){
            val x= getRandomCharacter("abcdefghijklmnopqrstuvwxyz")
            _mealCollection.postValue(mealRepo.getMealBySearch(x.toString()).meals)
        }
    }
}