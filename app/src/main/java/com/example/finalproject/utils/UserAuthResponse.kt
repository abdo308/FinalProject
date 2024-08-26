package com.app.domain.models

sealed class UserAuthResponse<out R>(){
    //The return value in case if the user authentication was successful
    data class AuthSuccessful<out R>(val user: R) : UserAuthResponse<R>()
    //The return value in case if the user authentication was a failure
    data class AuthFailed(val exception: Exception) : UserAuthResponse<Nothing>()
    //The return value in case if the user authentication is in progress
    data object Loading : UserAuthResponse<Nothing>()
}
