package com.example.illusionapp.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.illusionapp.view.auth.LoginActivity
import com.example.illusionapp.R
import com.example.illusionapp.utils.SharedPreferencesHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        // Check if user is not logged in
        if (!sharedPreferencesHelper.isUserLoggedIn()) {
            navigateToLogin()
            return // Exit early to prevent further initialization
        }

        // Setup NavHostFragment and NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // Setup Bottom Navigation
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        // Handle Floating Action Button (FAB) click
        val fab: FloatingActionButton = findViewById(R.id.scan_fab)
        fab.setOnClickListener {
            navController.navigate(R.id.scanFragment)
        }

        // Setup Bottom Navigation menu click for supported items
        setupBottomNavigation(bottomNavigationView)
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setupBottomNavigation(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.bottom_gallery -> {
                    navController.navigate(R.id.galleryFragment)
                    true
                }
                R.id.bottom_history -> {
                    navController.navigate(R.id.historyFragment)
                    true
                }
                R.id.bottom_profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }
}
