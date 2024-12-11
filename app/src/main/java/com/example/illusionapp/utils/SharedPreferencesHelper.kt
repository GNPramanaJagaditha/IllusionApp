package com.example.illusionapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser(username: String, password: String) {
        preferences.edit().apply {
            putString("username", username)
            putString("password", password)
            apply()
        }
    }

    fun getUsername(): String? = preferences.getString("username", "User")

    fun getPassword(): String? = preferences.getString("password", "Password")

    fun isUserLoggedIn(): Boolean {
        return preferences.getBoolean("is_logged_in", false)
    }

    fun setUserLoggedIn(loggedIn: Boolean) {
        preferences.edit().apply {
            putBoolean("is_logged_in", loggedIn)
            apply()
        }
    }

    fun validateCredentials(username: String, password: String): Boolean {
        val savedUsername = preferences.getString("username", null)
        val savedPassword = preferences.getString("password", null)
        return username == savedUsername && password == savedPassword
    }

    fun setProfilePhotoUri(uri: String) {
        preferences.edit().apply {
            putString("profile_photo_uri", uri)
            apply()
        }
    }

    fun getProfilePhotoUri(): String? = preferences.getString("profile_photo_uri", null)

    fun clearProfilePhotoUri() {
        preferences.edit().apply {
            remove("profile_photo_uri")
            apply()
        }
    }

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
