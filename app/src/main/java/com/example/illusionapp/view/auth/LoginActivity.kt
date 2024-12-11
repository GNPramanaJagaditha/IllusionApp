package com.example.illusionapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.illusionapp.R
import com.example.illusionapp.databinding.ActivityLoginBinding
import com.example.illusionapp.utils.SharedPreferencesHelper
import com.example.illusionapp.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_invisible, 0)
        binding.etPassword.setOnTouchListener { _, event ->
            if (event.rawX >= (binding.etPassword.right - binding.etPassword.compoundPaddingEnd)) {
                togglePasswordVisibility()
                true
            } else {
                false
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInputs(username, password)) {
                if (sharedPreferencesHelper.validateCredentials(username, password)) {
                    sharedPreferencesHelper.setUserLoggedIn(true)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvGotoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateInputs(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            binding.etUsername.error = "Username is required"
            return false
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            return false
        }
        return true
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_invisible, 0)
        } else {
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visible, 0)
        }
        binding.etPassword.setSelection(binding.etPassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }
}
