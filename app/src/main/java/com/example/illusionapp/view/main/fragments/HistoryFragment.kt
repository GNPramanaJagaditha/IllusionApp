package com.example.illusionapp.view.main.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.illusionapp.R
import com.example.illusionapp.databinding.FragmentHistoryBinding
import com.example.illusionapp.view.adapter.HistoryAdapter
import com.example.illusionapp.view.viewmodel.HistoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel: HistoryViewModel by viewModels()
    private val adapter by lazy { HistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHistoryBinding.bind(view)

        setupRecyclerView()
        observeHistory()
        setupSwipeToDelete()

        historyViewModel.fetchAndStoreHistory()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistoryFragment.adapter
        }
    }

    private fun observeHistory() {
        historyViewModel.allHistory.observe(viewLifecycleOwner) { historyList ->
            adapter.submitList(historyList)
        }
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val historyItem = adapter.currentList[position]

                historyViewModel.delete(historyItem)

                val fab = requireActivity().findViewById<FloatingActionButton>(R.id.scan_fab)

                Snackbar.make(binding.root, "History item deleted", Snackbar.LENGTH_LONG)
                    .setAnchorView(fab)
                    .setAction("UNDO") {
                        historyViewModel.insert(historyItem)
                    }.apply {
                        view.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                        setActionTextColor(ContextCompat.getColor(requireContext(), R.color.snackbar_action))
                    }
                    .show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewHistory)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
