package com.example.illusionapp.view.main.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.illusionapp.R
import com.example.illusionapp.utils.SharedPreferencesHelper
import com.example.illusionapp.view.auth.LoginActivity
import com.example.illusionapp.view.main.profile.AboutActivity
import com.example.illusionapp.view.main.profile.HelpActivity
import com.example.illusionapp.view.main.profile.ModeActivity
import com.example.illusionapp.view.main.profile.ProfileActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var profilePhoto: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())

        profilePhoto = view.findViewById(R.id.profile_photo)
        val modeOption = view.findViewById<LinearLayout>(R.id.modeOption)
        val logoutOption = view.findViewById<LinearLayout>(R.id.logoutOption)
        val profileOption = view.findViewById<LinearLayout>(R.id.profileOption)
        val helpOption = view.findViewById<LinearLayout>(R.id.helpOption)
        val aboutOption = view.findViewById<LinearLayout>(R.id.aboutOption)

        // Load profile photo on fragment load
        loadProfilePhoto()

        modeOption.setOnClickListener {
            val intent = Intent(requireContext(), ModeActivity::class.java)
            startActivity(intent)
        }

        profileOption.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        helpOption.setOnClickListener {
            val intent = Intent(requireContext(), HelpActivity::class.java)
            startActivity(intent)
        }

        aboutOption.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        logoutOption.setOnClickListener {
            handleLogout()
        }
    }

    private fun loadProfilePhoto() {
        // Retrieve the profile photo URI from SharedPreferences
        val photoUri = sharedPreferencesHelper.getProfilePhotoUri()
        if (!photoUri.isNullOrEmpty()) {
            // Load the profile photo using Glide and make it circular
            Glide.with(this)
                .load(Uri.parse(photoUri))
                .circleCrop() // Ensures the image is circular
                .placeholder(R.drawable.profile_container) // Default placeholder
                .into(profilePhoto)
        } else {
            // Load the default placeholder image
            Glide.with(this)
                .load(R.drawable.profile_container)
                .circleCrop()
                .into(profilePhoto)
        }
    }

    private fun handleLogout() {
        sharedPreferencesHelper.setUserLoggedIn(false) // Clear login state

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear activity stack
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        // Reload profile photo when returning to the fragment
        loadProfilePhoto()
    }
}
