package com.example.finalproject.ui.AuthenticationFragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.models.UserAuthResponse
import com.app.utils.isValidEmail
import com.example.finalproject.repo.ApplicationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val authRepo: ApplicationRepository
) : ViewModel() {

    private val _signInState = MutableLiveData<UserAuthResponse>(null)
    val signInState: LiveData<UserAuthResponse> = _signInState

    fun signIn(email: String, password: String) {
        var error = false
        _signInState.value = UserAuthResponse.Loading

        //Check if the email is in a valid format
        if (!email.isValidEmail()) {
            _signInState.value = UserAuthResponse.AuthFailed(Exception("email"))
            error = true
        }

        //Check if the user left the password field empty
        if (password.isEmpty()) {
            _signInState.value = UserAuthResponse.AuthFailed(Exception("Password"))
            error = true
        }

        if(error)
            return

        viewModelScope.launch(Dispatchers.IO) {
            val user = authRepo.getUserByEmail(email)


            if (user == null) {
                //If user is null then this email doesn't exist
                withContext(Dispatchers.Main)
                {
                    _signInState.value = UserAuthResponse.AuthFailed(Exception("Invalid email"))
                }
            } else {
                if (user.password == password) {
                    withContext(Dispatchers.Main)
                    {
                        _signInState.value = UserAuthResponse.AuthSuccessful
                    }
                } else {
                    withContext(Dispatchers.Main)
                    {
                        _signInState.value =
                            UserAuthResponse.AuthFailed(Exception("Invalid password"))
                    }
                }
            }
        }
    }
}