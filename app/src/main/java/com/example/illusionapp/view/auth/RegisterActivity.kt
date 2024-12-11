package com.example.illusionapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.illusionapp.R
import com.example.illusionapp.databinding.ActivityRegisterBinding
import com.example.illusionapp.utils.SharedPreferencesHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var isPasswordVisible = false // Toggle state for password visibility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        // Toggle password visibility
        binding.etCreatePassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_invisible, 0)
        binding.etCreatePassword.setOnTouchListener { _, event ->
            if (event.rawX >= (binding.etCreatePassword.right - binding.etCreatePassword.compoundPaddingEnd)) {
                togglePasswordVisibility(binding.etCreatePassword)
                true
            } else {
                false
            }
        }

        binding.etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_invisible, 0)
        binding.etConfirmPassword.setOnTouchListener { _, event ->
            if (event.rawX >= (binding.etConfirmPassword.right - binding.etConfirmPassword.compoundPaddingEnd)) {
                togglePasswordVisibility(binding.etConfirmPassword)
                true
            } else {
                false
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etCreatePassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (validateInputs(username, email, password, confirmPassword)) {
                sharedPreferencesHelper.saveUser(username, password)
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.tvGotoRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validateInputs(username: String, email: String, password: String, confirmPassword: String): Boolean {
        if (username.isEmpty()) {
            binding.etUsername.error = "Username is required"
            return false
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Enter a valid email"
            return false
        }
        if (password.isEmpty() || password.length < 8) {
            binding.etCreatePassword.error = "Password must be at least 6 characters"
            return false
        }
        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords do not match"
            return false
        }
        return true
    }

    private fun togglePasswordVisibility(editText: EditText) {
        if (isPasswordVisible) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_invisible, 0)
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visible, 0)
        }
        editText.setSelection(editText.text.length) // Retain cursor position
        isPasswordVisible = !isPasswordVisible
    }
}
