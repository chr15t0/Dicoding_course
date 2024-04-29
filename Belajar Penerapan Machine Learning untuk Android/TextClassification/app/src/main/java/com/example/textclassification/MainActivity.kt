package com.example.textclassification

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.textclassification.databinding.ActivityMainBinding
import com.google.mediapipe.tasks.components.containers.Classifications
import com.google.mediapipe.tasks.text.textclassifier.TextClassifier
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textClassifierHelper = TextClassifierHelper(
            context = this,
            classifierListener = object:TextClassifierHelper.ClassifierListener{
                override fun onError(error: String) {
                    Toast.makeText(this@MainActivity,error,Toast.LENGTH_SHORT).show()
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let {it->
                            if (it.isNotEmpty() && it[0].categories().isNotEmpty()){
                                println(it)
                                val sortedCategories =
                                    it[0].categories().sortedByDescending { it.score() }

                                val displayResults =
                                    sortedCategories.joinToString("\n"){
                                        "${it.categoryName()}"+NumberFormat.getPercentInstance()
                                            .format(it.score()).trim()
                                    }
                                binding.tvResult.text = displayResults
                            }else{
                                binding.tvResult.text = ""
                            }
                        }
                    }
                }
            }
        )

        binding.btnClassify.setOnClickListener{
            val inputText = binding.edInput.text.toString()
            textClassifierHelper.classify(inputText)
        }

    }
}