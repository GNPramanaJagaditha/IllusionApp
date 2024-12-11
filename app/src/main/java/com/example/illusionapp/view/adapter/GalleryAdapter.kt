package com.example.illusionapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.illusionapp.R
import com.example.illusionapp.data.dummy.GalleryItem

class GalleryAdapter(private val items: List<GalleryItem>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val probabilityText: TextView = view.findViewById(R.id.probabilityText)
        val descriptionText: TextView = view.findViewById(R.id.descriptionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = items[position]
        holder.probabilityText.text = "AI Probability: ${item.probability}%"
        holder.descriptionText.text = item.description
        holder.imageView.setImageResource(item.imageResId)
    }

    override fun getItemCount(): Int = items.size
}
