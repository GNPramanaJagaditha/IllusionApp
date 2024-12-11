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

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                profilePhotoUri = it

                Glide.with(this)
                    .load(profilePhotoUri)
                    .circleCrop()
                    .placeholder(com.example.illusionapp.R.drawable.profile_container)
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
                .placeholder(com.example.illusionapp.R.drawable.profile_container)
                .into(binding.profilePhoto)
        } else {
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
