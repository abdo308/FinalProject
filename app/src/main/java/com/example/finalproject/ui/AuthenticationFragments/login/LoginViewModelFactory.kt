package com.example.finalproject.ui.AuthenticationFragments.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.repo.ApplicationRepository

class LoginViewModelFactory(
    private val authRepo: ApplicationRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(LoginViewModel::class.java))
        {
            LoginViewModel(authRepo) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}