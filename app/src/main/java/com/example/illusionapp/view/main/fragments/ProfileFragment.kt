package com.example.illusionapp.view.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.illusionapp.R
import com.example.illusionapp.view.main.profile.ModeActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val modeOption = view.findViewById<LinearLayout>(R.id.modeOption)

        modeOption.setOnClickListener {
            val intent = Intent(requireContext(), ModeActivity::class.java)
            startActivity(intent)
        }
    }
}
