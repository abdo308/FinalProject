package com.example.finalproject.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.AlternativeSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.FirstFragment,R.id.searchFragment,R.id.favoriteFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentDestinationId = navController.currentDestination?.id
                if(currentDestinationId==R.id.FirstFragment){
                    finish()
                }
                else
                {
                    val x=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                    x.visibility=View.VISIBLE
                    navController.navigateUp()
                }

            }

        })

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        val bottomNavView:BottomNavigationView=findViewById(R.id.bottomNavigationView)
        bottomNavView.visibility=View.VISIBLE
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("msg","Finshed")
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sign_out -> {
                showConfirmationMessage()
                return true
            }

            R.id.action_about_us -> {
                val navController = findNavController(R.id.nav_host_fragment_content_home)
                val bottomNavView : BottomNavigationView = findViewById(R.id.bottomNavigationView)
                bottomNavView.visibility = View.GONE
                if(navController.currentDestination?.id != R.id.aboutUsFragment)
                navController.navigate(R.id.aboutUsFragment)
                return true
            }
        }
        return false
    }

    fun showConfirmationMessage() {
        val dialogView = layoutInflater.inflate(R.layout.confirmation_message_view,null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val yesButton = dialogView.findViewById<Button>(R.id.button_yes)
        val noButton = dialogView.findViewById<Button>(R.id.button_no)
        yesButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val sharedPreference = getSharedPreferences("key", Context.MODE_PRIVATE)
            Toast.makeText(this,"Signed Out Successfully",Toast.LENGTH_LONG).show()
            with(sharedPreference.edit()) {
                putBoolean("isLoggedIn", false)
                putString("Email",null)
                apply()
            }
            startActivity(intent)
            finish()
        }
        noButton.setOnClickListener {
            dialog.hide()
        }

    }
}
