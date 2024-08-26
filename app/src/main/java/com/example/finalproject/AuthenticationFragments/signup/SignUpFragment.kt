package com.example.finalproject.AuthenticationFragments.signup

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.app.domain.models.UserAuthResponse
import com.example.finalproject.HomeActivity
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack(R.id.signUpFragment, true)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //The sign up button listener
        binding.signUpBtn.setOnClickListener {

            hideAllSignUpErrors()

            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.signUp(name, email, password)
        }


        //This is needed to invoke the click listener when the view is out of focus
        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.passwordEditText.performClick() // Trigger the click event
            }
        }

        //Remove the error message so the user can use the show password button
        binding.passwordEditText.setOnClickListener {
            binding.passwordTextInputLayout.error = null
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.signUpState.observe(viewLifecycleOwner){
            when (it) {
                is UserAuthResponse.AuthFailed -> {
                    binding.signUpBtn.isEnabled = true
                    showSignUpError(it.exception.message.toString())
                    Log.d(
                        "Authentication",
                        "Register failed. ${it.exception.message.toString()}"
                    )
                }

                is UserAuthResponse.AuthSuccessful -> {
                    goToHomeScreen()
                    Log.d("Authentication", "Register success.")
                }

                UserAuthResponse.Loading -> {
                    //Disable the signup button as a sign of loading
                    binding.signUpBtn.isEnabled = false
                    Log.d("Authentication", "Register loading.")
                }

                else -> Unit
            }
        }
    }

    private fun goToHomeScreen() {
        val intent = Intent(this.context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun showSignUpError(msg: String) {
        when (msg) {
            "name" -> binding.nameTextInputLayout.error = "Name can't be empty."
            "email" -> binding.emailTextInputLayout.error = "Invalid email format."
            "Password" -> binding.passwordTextInputLayout.error =
                "Password must contain lower and " +
                        "upper characters, numbers and special characters, and be more than 8 characters long."

            "The email address is already in use by another account." ->
                binding.emailTextInputLayout.error = msg

        }
    }

    private fun hideAllSignUpErrors() {
        binding.nameTextInputLayout.error = null
        binding.emailTextInputLayout.error = null
        binding.passwordTextInputLayout.error = null
    }
}