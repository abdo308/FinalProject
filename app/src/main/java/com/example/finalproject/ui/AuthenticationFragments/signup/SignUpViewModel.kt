package com.example.finalproject.ui.AuthenticationFragments.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.models.UserAuthResponse
import com.app.utils.isValidEmail
import com.app.utils.isValidPassword
import com.example.finalproject.models.User
import com.example.finalproject.repo.ApplicationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(private val authRepo: ApplicationRepository) : ViewModel() {

    private val _signUpState = MutableLiveData<UserAuthResponse>(null)
    val signUpState: LiveData<UserAuthResponse> = _signUpState


    fun signUp(firstName: String, lastName: String, email: String, password: String)
    {
        var error = false

        if (firstName.isEmpty()){
            _signUpState.value = UserAuthResponse.AuthFailed(Exception("FName"))
            error = true
        }

        if (lastName.isEmpty())
        {
            _signUpState.value = UserAuthResponse.AuthFailed(Exception("LName"))
            error = true
        }

        //Check if the email is in a valid format
        if (!email.isValidEmail()){
            _signUpState.value = UserAuthResponse.AuthFailed(Exception("email"))
            error = true
        }

        //Check if the user left the password field empty
        if (!password.isValidPassword())
        {
            _signUpState.value = UserAuthResponse.AuthFailed(Exception("Password"))
            error = true
        }

        if(error)
            return

        //Check if the email already exists
        viewModelScope.launch(Dispatchers.IO) {
            val user = authRepo.getUserByEmail(email)

            if(user != null)
            {
                withContext(Dispatchers.Main)
                {
                    _signUpState.value = UserAuthResponse.AuthFailed(Exception("Duplicate email"))
                }
            }
            else{
                authRepo.insertNewUser(User(email, password, firstName, lastName))

                withContext(Dispatchers.Main)
                {
                    _signUpState.value = UserAuthResponse.AuthSuccessful
                }
            }

        }
    }
}