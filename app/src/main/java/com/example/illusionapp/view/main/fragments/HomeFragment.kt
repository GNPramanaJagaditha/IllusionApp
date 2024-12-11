package com.example.illusionapp.view.main.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.illusionapp.R
import com.example.illusionapp.data.dummy.NewsItem
import com.example.illusionapp.utils.SharedPreferencesHelper
import com.example.illusionapp.view.adapter.HistoryAdapter
import com.example.illusionapp.view.adapter.NewsAdapter
import com.example.illusionapp.view.main.profile.ProfileActivity
import com.example.illusionapp.view.viewmodel.HistoryViewModel

class HomeFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var profileImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())

        // Profile photo
        profileImageView = root.findViewById(R.id.profile_photo)
        loadProfilePhoto()

        // Initialize RecyclerView for recent scans
        val recentScansRecyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_history)
        recentScansRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        historyAdapter = HistoryAdapter()
        recentScansRecyclerView.adapter = historyAdapter

        // Initialize RecyclerView for news
        val newsRecyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_news)
        newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val newsItems = listOf(
            NewsItem(R.drawable.news1, "TechCrunch", "AI Revolution in Full Swing"),
            NewsItem(R.drawable.news2, "BBC News", "Climate Change and You"),
            NewsItem(R.drawable.news3, "The Verge", "Latest Gadgets Unveiled"),
        )

        val newsAdapter = NewsAdapter(newsItems) { newsItem ->
            Toast.makeText(
                requireContext(),
                "Source: ${newsItem.source}\nTitle: ${newsItem.title}",
                Toast.LENGTH_LONG
            ).show()
        }
        newsRecyclerView.adapter = newsAdapter

        // Set click listener for the profile image
        profileImageView.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        observeRecentScans()
        setupViewAllClickListener()
    }

    private fun observeRecentScans() {
        historyViewModel.getRecentScans(5).observe(viewLifecycleOwner) { recentScans ->
            historyAdapter.submitList(recentScans)
        }
    }

    private fun setupViewAllClickListener() {
        val viewAllButton = view?.findViewById<TextView>(R.id.tv_view_all)
        viewAllButton?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_historyFragment)

            val bottomNavigationView = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigationView.selectedItemId = R.id.bottom_history
        }
    }

    private fun loadProfilePhoto() {
        // Retrieve the profile photo URI from SharedPreferences
        val photoUri = sharedPreferencesHelper.getProfilePhotoUri()
        if (!photoUri.isNullOrEmpty()) {
            Glide.with(this)
                .load(Uri.parse(photoUri))
                .circleCrop() // Ensures the image is circular
                .placeholder(R.drawable.profile_container) // Default placeholder
                .into(profileImageView)
        } else {
            // Load the default placeholder image
            Glide.with(this)
                .load(R.drawable.profile_container)
                .circleCrop()
                .into(profileImageView)
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload profile photo when returning to the fragment
        loadProfilePhoto()
    }
}
