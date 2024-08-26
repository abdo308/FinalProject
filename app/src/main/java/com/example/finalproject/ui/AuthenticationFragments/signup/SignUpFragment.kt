package com.example.finalproject.ui.AuthenticationFragments.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.domain.models.UserAuthResponse
import com.example.finalproject.ui.HomeActivity
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentSignUpBinding
import com.example.finalproject.db.LocalDataSourceImpl
import com.example.finalproject.network.APIClient
import com.example.finalproject.repo.ApplicationRepoImpl


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

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

            val fName = binding.firstNameEditText.text.toString()
            val lName = binding.lastNameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.signUp(fName, lName, email, password)
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

        initViewModel()

        subscribeToObservers()
    }

    private fun initViewModel()
    {
        val factory = SignUpViewModelFactory(ApplicationRepoImpl(LocalDataSourceImpl(requireContext()),APIClient))
        viewModel = ViewModelProvider(this, factory).get(SignUpViewModel::class.java)
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
            "FName" -> binding.firstNameTextInputLayout.error = "First name can't be empty."
            "LName" -> binding.lastNameTextInputLayout.error = "Last name can't be empty."
            "email" -> binding.emailTextInputLayout.error = "Invalid email format."
            "Password" -> binding.passwordTextInputLayout.error =
                "Password must contain lower and " +
                        "upper characters, numbers and special characters, and be more than 8 characters long."

            "Duplicate email" ->
                binding.emailTextInputLayout.error = "The email address is already in use by another account."

        }
    }

    private fun hideAllSignUpErrors() {
        binding.firstNameTextInputLayout.error = null
        binding.lastNameTextInputLayout.error = null
        binding.emailTextInputLayout.error = null
        binding.passwordTextInputLayout.error = null
    }
}