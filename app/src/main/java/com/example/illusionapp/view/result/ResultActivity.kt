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

        supportActionBar?.hide()

        val predictedLabel = intent.getStringExtra("predicted_label")
        val confidence = intent.getFloatExtra("confidence", 0.0f)
        val imageUri = intent.getStringExtra("image_uri")

        binding.tvAi.text = when (predictedLabel?.lowercase()) {
            "real" -> getString(R.string.ai_not_detected)
            "fake" -> getString(R.string.ai_is_detected)
            else -> getString(R.string.ai_unknown)
        }

        binding.tvAccuracy.text = getString(R.string.accuracy, confidence * 100)

        when (predictedLabel?.lowercase()) {
            "fake" -> binding.tvConfirmation.text = getString(R.string.confirmation_fake)
            "real" -> binding.tvConfirmation.text = getString(R.string.confirmation_real)
            else -> binding.tvConfirmation.text = getString(R.string.confirmation_default)
        }

        if (!imageUri.isNullOrEmpty()) {
            Glide.with(this)
                .load(Uri.parse(imageUri))
                .placeholder(R.drawable.image_container)
                .into(binding.imageResult)
        }

        binding.btnDone.setOnClickListener {
            finish()
        }
    }
}
