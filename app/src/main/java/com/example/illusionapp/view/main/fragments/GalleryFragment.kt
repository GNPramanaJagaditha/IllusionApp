package com.example.illusionapp.view.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.illusionapp.R
import com.example.illusionapp.data.dummy.GalleryItem
import com.example.illusionapp.view.adapter.GalleryAdapter

class GalleryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewGallery)

        val dummyData = listOf(
            GalleryItem(R.drawable.photo_1, 85, "Photo of a Young Woman"),
            GalleryItem(R.drawable.photo_2, 92, "Jennie Kim - Edit"),
            GalleryItem(R.drawable.photo_3, 90, "Photo of a Dog"),
            GalleryItem(R.drawable.photo_4, 50, "Photo of an Anime Character"),
            GalleryItem(R.drawable.photo_5, 80, "Photo of an Woman"),
            GalleryItem(R.drawable.photo_3, 90, "Photo of an Animal"),
            GalleryItem(R.drawable.photo_2, 92, "Jennie Kim")
        )

        adapter = GalleryAdapter(dummyData)
        recyclerView.adapter = adapter
    }
}
