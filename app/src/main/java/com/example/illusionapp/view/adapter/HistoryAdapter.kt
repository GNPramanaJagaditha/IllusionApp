package com.example.illusionapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.illusionapp.R
import com.example.illusionapp.data.local.entity.History
import com.example.illusionapp.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<History, HistoryAdapter.HistoryViewHolder>(DiffCallback()) {

    class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.historyLabel.text = history.label
            binding.historyTime.text = history.timestamp

            Glide.with(binding.historyImage.context)
                .load(history.imageUri)
                .placeholder(R.drawable.image_container)
                .into(binding.historyImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: History, newItem: History) = oldItem == newItem
    }
}
