package com.example.illusionapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Save user credentials
    fun saveUser(username: String, password: String) {
        preferences.edit().apply {
            putString("username", username)
            putString("password", password)
            apply()
        }
    }

    // Retrieve username
    fun getUsername(): String? = preferences.getString("username", "User")

    // Retrieve password
    fun getPassword(): String? = preferences.getString("password", "Password")

    // Check if user is logged in
    fun isUserLoggedIn(): Boolean {
        return preferences.getBoolean("is_logged_in", false)
    }

    // Update login state
    fun setUserLoggedIn(loggedIn: Boolean) {
        preferences.edit().apply {
            putBoolean("is_logged_in", loggedIn)
            apply()
        }
    }

    // Validate credentials
    fun validateCredentials(username: String, password: String): Boolean {
        val savedUsername = preferences.getString("username", null)
        val savedPassword = preferences.getString("password", null)
        return username == savedUsername && password == savedPassword
    }

    // Save profile photo URI
    fun setProfilePhotoUri(uri: String) {
        preferences.edit().apply {
            putString("profile_photo_uri", uri)
            apply()
        }
    }

    // Retrieve profile photo URI
    fun getProfilePhotoUri(): String? = preferences.getString("profile_photo_uri", null)

    // Clear profile photo URI
    fun clearProfilePhotoUri() {
        preferences.edit().apply {
            remove("profile_photo_uri")
            apply()
        }
    }

    // Clear all user data (logout action)
    fun clearUserData() {
        preferences.edit().apply {
            remove("username")
            remove("password")
            remove("is_logged_in")
            remove("profile_photo_uri")
            apply()
        }
    }
}
