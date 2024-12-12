package com.example.illusionapp.view.main.profile

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.illusionapp.R
import com.example.illusionapp.databinding.ActivityProfileBinding
import com.example.illusionapp.utils.SharedPreferencesHelper
import com.example.illusionapp.view.auth.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var profilePhotoUri: Uri? = null

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                profilePhotoUri = it

                Glide.with(this)
                    .load(profilePhotoUri)
                    .circleCrop()
                    .placeholder(R.drawable.profile_container)
                    .into(binding.profilePhoto)

                sharedPreferencesHelper.setProfilePhotoUri(profilePhotoUri.toString())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Profile"
            setDisplayHomeAsUpEnabled(true)
        }

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        loadUserData()

        binding.btnUploadPhoto.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        binding.btnDeleteAccount.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun loadUserData() {
        binding.tvUsername.text = sharedPreferencesHelper.getUsername()
        binding.tvPassword.text = sharedPreferencesHelper.getPassword()

        val savedPhotoUri = sharedPreferencesHelper.getProfilePhotoUri()
        if (!savedPhotoUri.isNullOrEmpty()) {
            profilePhotoUri = Uri.parse(savedPhotoUri)
            Glide.with(this)
                .load(profilePhotoUri)
                .circleCrop()
                .placeholder(R.drawable.profile_container)
                .into(binding.profilePhoto)
        } else {
            Glide.with(this)
                .load(R.drawable.profile_container)
                .circleCrop()
                .into(binding.profilePhoto)
        }
    }

    private fun showDeleteConfirmationDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Delete", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).apply {
                setTextColor(Color.WHITE)
                setBackgroundColor(Color.TRANSPARENT)
                setOnClickListener {
                    dialog.dismiss()
                }
            }
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).apply {
                setTextColor(Color.RED)
                setBackgroundColor(Color.TRANSPARENT)
                setOnClickListener {
                    deleteAccount()
                }
            }
        }

        dialog.show()
    }

    private fun deleteAccount() {
        sharedPreferencesHelper.clearUserData()
        sharedPreferencesHelper.setUserLoggedIn(false)

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}