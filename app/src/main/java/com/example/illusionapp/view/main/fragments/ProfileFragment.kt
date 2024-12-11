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
        val photoUri = sharedPreferencesHelper.getProfilePhotoUri()
        if (!photoUri.isNullOrEmpty()) {
            Glide.with(this)
                .load(Uri.parse(photoUri))
                .circleCrop()
                .placeholder(R.drawable.profile_container)
                .into(profilePhoto)
        } else {
            Glide.with(this)
                .load(R.drawable.profile_container)
                .circleCrop()
                .into(profilePhoto)
        }
    }

    private fun handleLogout() {
        sharedPreferencesHelper.setUserLoggedIn(false)
        sharedPreferencesHelper.clearProfilePhotoUri()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadProfilePhoto()
    }
}
