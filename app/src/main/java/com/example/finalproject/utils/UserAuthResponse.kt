package com.app.domain.models

sealed class UserAuthResponse(){
    //The return value in case if the user authentication was successful
    data object AuthSuccessful : UserAuthResponse()
    //The return value in case if the user authentication was a failure
    data class AuthFailed(val exception: Exception) : UserAuthResponse()
    //The return value in case if the user authentication is in progress
    data object Loading : UserAuthResponse()
}
