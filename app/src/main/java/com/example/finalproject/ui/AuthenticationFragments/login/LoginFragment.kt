package com.example.finalproject.ui.AuthenticationFragments.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.domain.models.UserAuthResponse
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentLoginBinding
import com.example.finalproject.db.LocalDataSourceImpl
import com.example.finalproject.network.APIClient
import com.example.finalproject.network.RemoteDataSource
import com.example.finalproject.repo.ApplicationRepoImpl
import com.example.finalproject.ui.HomeActivity

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViewModel()

        subscribeToObservers()

        //setting up the click listener for the create an account button
        binding.registerUserBtn.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_sign_up_navigation)
        }

        binding.signInBtn.setOnClickListener { onSignInClick() }

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
    }

    private fun initViewModel() {
        val factory =
            LoginViewModelFactory(ApplicationRepoImpl(LocalDataSourceImpl(requireContext())))
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    private fun goToHomeScreen() {
        val intent = Intent(this.context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun onSignInClick() {
        hideAllSignInErrors()

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        viewModel.signIn(email, password)
    }

    private fun subscribeToObservers() {

        viewModel.signInState.observe(viewLifecycleOwner) {
            when (it) {
                is UserAuthResponse.AuthFailed -> {
                    binding.signInBtn.isEnabled = true
                    showSignInError(it.exception.message.toString())
                    Log.d("Authentication", "Register failed. ${it.exception.message.toString()}")
                }

                is UserAuthResponse.AuthSuccessful -> {
                    val sharedPreference=requireContext().getSharedPreferences("key", Context.MODE_PRIVATE)
                    with(sharedPreference.edit()){
                    putBoolean("isLoggedIn",true)
                    apply()
                    }
                    goToHomeScreen()
                    Log.d("Authentication", "Register success.")
                }

                UserAuthResponse.Loading -> {
                    //Disable the signup button as a sign of loading
                    binding.signInBtn.isEnabled = false
                    Log.d("Authentication", "Register loading.")
                }

                else -> Unit
            }
        }
    }

    private fun showSignInError(msg: String) {
        when(msg) {
            "email" -> binding.emailTextInputLayout.error = "Invalid email format."
            "Password" -> binding.passwordTextInputLayout.error =
                "Password must be at least 8 characters long."

            "Invalid email" -> binding.emailTextInputLayout.error = "Email doesn't exist!"
            "Invalid password" -> binding.passwordTextInputLayout.error =
                "Incorrect password!"
        }
    }

    private fun hideAllSignInErrors() {
        binding.emailTextInputLayout.error = null
        binding.passwordTextInputLayout.error = null
    }
}
