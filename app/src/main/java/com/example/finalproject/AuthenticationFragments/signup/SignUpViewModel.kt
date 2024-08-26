package com.example.finalproject.AuthenticationFragments.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.domain.models.UserAuthResponse
import com.app.utils.isValidEmail
import com.app.utils.isValidPassword

class SignUpViewModel : ViewModel() {

    private val _signUpState = MutableLiveData<UserAuthResponse<Nothing>>(null)
    val signUpState: LiveData<UserAuthResponse<Nothing>?> = _signUpState


    fun signUp(name: String, email: String, password: String)
    {
        if (name.isEmpty())
            _signUpState.value = UserAuthResponse.AuthFailed(Exception("name"))

        //Check if the email is in a valid format
        if (!email.isValidEmail())
            _signUpState.value = UserAuthResponse.AuthFailed(Exception("email"))

        //Check if the user left the password field empty
        if (!password.isValidPassword())
            _signUpState.value = UserAuthResponse.AuthFailed(Exception("Password"))

        //Check if the email already exists

    }
}