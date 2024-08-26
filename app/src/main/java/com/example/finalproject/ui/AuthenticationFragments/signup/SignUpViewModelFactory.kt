package com.example.finalproject.ui.AuthenticationFragments.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.repo.ApplicationRepository

class SignUpViewModelFactory(private val authRepo: ApplicationRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SignUpViewModel::class.java))
        {
            SignUpViewModel(authRepo) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}