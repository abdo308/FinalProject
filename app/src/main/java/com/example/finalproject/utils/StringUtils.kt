package com.app.utils

import android.util.Patterns

/**
 * @brief Extension function to check if a string is a valid email address.
 */
fun String?.isValidEmail() = !isNullOrEmpty()
        && Patterns.EMAIL_ADDRESS.matcher(this).matches()
/**
 * @brief Extension function to check if a string is a valid phone number.
 */
fun String?.isValidPhoneNum() = !isNullOrEmpty()
        && Patterns.PHONE.matcher(this).matches()

/**
 * @brief Extension function to check if a string is a valid password.
 */
fun String?.isValidPassword() : Boolean{
    if(this.isNullOrEmpty())
        return false

    val upperCaseRegex = Regex("[A-Z]")
    val lowerCaseRegex = Regex("[a-z]")
    val digitRegex = Regex("\\d")
    val specialCharRegex = Regex("[^A-Za-z0-9]")

    return this.length >= 8 && // Minimum length check (optional)
            upperCaseRegex.containsMatchIn(this) &&
            lowerCaseRegex.containsMatchIn(this) &&
            digitRegex.containsMatchIn(this) &&
            specialCharRegex.containsMatchIn(this)
}