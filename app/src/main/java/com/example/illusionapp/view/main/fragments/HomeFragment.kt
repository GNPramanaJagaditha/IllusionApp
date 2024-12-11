package com.example.illusionapp.view.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.illusionapp.R
import com.example.illusionapp.view.adapter.HistoryAdapter
import com.example.illusionapp.view.viewmodel.HistoryViewModel

class HomeFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter // Your custom adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize RecyclerView
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_history)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        historyAdapter = HistoryAdapter()
        recyclerView.adapter = historyAdapter

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
            // Navigate to HistoryFragment
            findNavController().navigate(R.id.action_homeFragment_to_historyFragment)

            // Highlight the "History" tab in BottomNavigationView
            val bottomNavigationView = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigationView.selectedItemId = R.id.bottom_history // Replace with your actual menu item ID
        }
    }
}
