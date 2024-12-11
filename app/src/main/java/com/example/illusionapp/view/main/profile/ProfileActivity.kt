package com.example.illusionapp.view.main.profile

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.illusionapp.databinding.ActivityProfileBinding
import com.example.illusionapp.utils.SharedPreferencesHelper

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var profilePhotoUri: Uri? = null

    // Register an activity result launcher for selecting an image
    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                profilePhotoUri = it
                // Load the selected image into the profile photo ImageView
                Glide.with(this)
                    .load(profilePhotoUri)
                    .circleCrop() // Make the image circular
                    .placeholder(com.example.illusionapp.R.drawable.profile_container) // Default placeholder
                    .into(binding.profilePhoto)

                // Save the photo URI to shared preferences
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

        // Load user data
        loadUserData()

        // Handle upload photo button click
        binding.btnUploadPhoto.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }
    }

    private fun loadUserData() {
        // Load username and password from SharedPreferences
        binding.tvUsername.text = sharedPreferencesHelper.getUsername()
        binding.tvPassword.text = sharedPreferencesHelper.getPassword()

        // Load profile photo if available
        val savedPhotoUri = sharedPreferencesHelper.getProfilePhotoUri()
        if (!savedPhotoUri.isNullOrEmpty()) {
            profilePhotoUri = Uri.parse(savedPhotoUri)
            Glide.with(this)
                .load(profilePhotoUri)
                .circleCrop() // Ensure circular crop when loading
                .placeholder(com.example.illusionapp.R.drawable.profile_container)
                .into(binding.profilePhoto)
        } else {
            // Load placeholder if no photo is available
            Glide.with(this)
                .load(com.example.illusionapp.R.drawable.profile_container)
                .circleCrop()
                .into(binding.profilePhoto)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
