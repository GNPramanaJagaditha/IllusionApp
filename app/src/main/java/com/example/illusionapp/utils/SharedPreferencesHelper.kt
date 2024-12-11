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
}
