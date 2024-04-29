package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    companion object{
        const val IMAGE_RESULT = "image_result"
        const val EXTRA_RESULT = "extra_result"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra(IMAGE_RESULT)
        binding.resultImage.setImageURI(Uri.parse(imageUri))

        val resultClassification = intent.getStringExtra(EXTRA_RESULT)
        binding.resultText.text = resultClassification
    }


}