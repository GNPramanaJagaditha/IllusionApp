package com.example.illusionapp.view.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.illusionapp.R
import com.example.illusionapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from Intent
        val predictedLabel = intent.getStringExtra("predicted_label")
        val confidence = intent.getFloatExtra("confidence", 0.0f)
        val imageUri = intent.getStringExtra("image_uri")

        // Display prediction results
        binding.tvAi.text = getString(R.string.ai_detected, predictedLabel)
        binding.tvAccuracy.text = getString(R.string.accuracy, confidence * 100)

        // Dynamically set confirmation text based on the result
        when (predictedLabel?.lowercase()) {
            "fake" -> binding.tvConfirmation.text = getString(R.string.confirmation_fake)
            "real" -> binding.tvConfirmation.text = getString(R.string.confirmation_real)
            else -> binding.tvConfirmation.text = getString(R.string.confirmation_default)
        }

        // Load the image into the ImageView
        if (!imageUri.isNullOrEmpty()) {
            Glide.with(this)
                .load(Uri.parse(imageUri))
                .placeholder(R.drawable.image_container)
                .into(binding.imageResult)
        }

        // Done button functionality
        binding.btnDone.setOnClickListener {
            finish()
        }

        // Share button functionality
        binding.btnShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "AI Prediction: $predictedLabel (Accuracy: ${"%.2f%%".format(confidence * 100)})"
                )
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }
}
