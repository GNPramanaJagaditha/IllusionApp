package com.example.illusionapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.illusionapp.R
import com.example.illusionapp.data.local.entity.History
import com.example.illusionapp.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<History, HistoryAdapter.HistoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        // Inflate binding for improved view handling
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val confidenceTextView: TextView = binding.root.findViewById(R.id.history_confidence)
        private val labelTextView: TextView = binding.root.findViewById(R.id.history_label)

        fun bind(history: History) {
            // Use data-binding for default fields
            binding.historyLabel.text = history.label
            binding.historyTime.text = history.timestamp

            // Load image with Glide
            Glide.with(binding.historyImage.context)
                .load(history.imageUri)
                .placeholder(R.drawable.image_container)
                .into(binding.historyImage)


            confidenceTextView.text = "Confidence: ${(history.confidence * 100).toInt()}%"
            labelTextView.text = when (history.label.lowercase()) {
                "real" -> "AI Not Detected"
                "fake" -> "AI Detected"
                else -> "Unknown"
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: History, newItem: History) = oldItem == newItem
    }
}
