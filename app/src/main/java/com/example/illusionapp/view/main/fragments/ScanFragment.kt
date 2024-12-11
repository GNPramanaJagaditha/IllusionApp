package com.example.illusionapp.view.main.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.illusionapp.R
import com.example.illusionapp.data.local.entity.History
import com.example.illusionapp.utils.FileUtils
import com.example.illusionapp.view.result.ResultActivity
import com.example.illusionapp.view.viewmodel.HistoryViewModel
import com.example.illusionapp.view.viewmodel.PredictionViewModel
import com.yalantis.ucrop.UCrop
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ScanFragment : Fragment(R.layout.fragment_scan) {

    private val viewModel: PredictionViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()

    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var scanningText: TextView
    private lateinit var imageView: ImageView
    private var shouldResetImage: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnUpload = view.findViewById<Button>(R.id.btn_upload)
        progressBar = view.findViewById(R.id.progress_bar)
        imageView = view.findViewById(R.id.imageContainer)
        scanningText = view.findViewById(R.id.scanningText)

        btnUpload.setOnClickListener {
            selectImage()
        }

        observeViewModel()
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            Log.d("ScanFragment", "Selected imageUri: $imageUri")
            imageUri?.let { startImageCropping(it) }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            val croppedUri = UCrop.getOutput(data!!)
            croppedUri?.let { handleCroppedImage(it) }
        }
    }

    private fun startImageCropping(uri: Uri) {
        val timestamp = System.currentTimeMillis()
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped_image_$timestamp.jpg"))
        UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1080, 1080)
            .start(requireContext(), this)
    }


    private fun handleCroppedImage(croppedUri: Uri) {
        imageUri = croppedUri
        Log.d("ScanFragment", "Cropped imageUri: $imageUri")
        shouldResetImage = false
        Glide.with(this)
            .load(croppedUri)
            .into(imageView)

        val file = FileUtils.getFileFromUri(requireContext(), croppedUri)
        file?.let {
            viewModel.fetchPrediction(it)
        }
    }

    override fun onResume() {
        super.onResume()
        if (shouldResetImage) resetToPlaceholder()
    }

    private fun resetToPlaceholder() {
        Glide.with(this)
            .load(R.drawable.image_container)
            .into(imageView)
        imageUri = null
    }

    private fun observeViewModel() {
        viewModel.predictionResult.observe(viewLifecycleOwner, Observer { result ->
            progressBar.visibility = View.GONE
            scanningText.visibility = View.GONE
            result?.let {
                Log.d(
                    "ScanFragment",
                    "Prediction Result: ${it.data.predicted_label}, Confidence: ${it.data.confidence}"
                )
                shouldResetImage = true

                val currentTimeMillis = System.currentTimeMillis()
                val formattedTimestamp = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                    .format(Date(currentTimeMillis))

                val intent = Intent(requireContext(), ResultActivity::class.java).apply {
                    putExtra("predicted_label", it.data.predicted_label)
                    putExtra("confidence", it.data.confidence)
                    putExtra("image_uri", imageUri.toString())
                }

                val history = History(
                    label = it.data.predicted_label,
                    title = File(imageUri.toString()).name,
                    timestamp = formattedTimestamp,
                    confidence = it.data.confidence,
                    imageUri = imageUri.toString()
                )
                historyViewModel.insert(history)

                startActivity(intent)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            scanningText.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }
}
