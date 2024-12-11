package com.example.illusionapp.view.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.illusionapp.R
import com.example.illusionapp.utils.SharedPreferencesHelper
import com.example.illusionapp.view.auth.LoginActivity
import com.example.illusionapp.view.main.profile.ModeActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val modeOption = view.findViewById<LinearLayout>(R.id.modeOption)
        val logoutOption = view.findViewById<LinearLayout>(R.id.logoutOption) // Add the logout LinearLayout ID

        modeOption.setOnClickListener {
            val intent = Intent(requireContext(), ModeActivity::class.java)
            startActivity(intent)
        }

        logoutOption.setOnClickListener {
            handleLogout()
        }
    }

    private fun handleLogout() {
        val sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        sharedPreferencesHelper.setUserLoggedIn(false) // Clear login state

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear activity stack
        startActivity(intent)
    }
}
