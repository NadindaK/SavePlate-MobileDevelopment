package com.dicoding.saveplate.ui.scan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.saveplate.R
import com.dicoding.saveplate.databinding.ActivityResultBinding
import org.json.JSONObject


class ResultActivity : AppCompatActivity() {


    private var _binding: ActivityResultBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Result"

        val confidence = intent.getStringExtra("confidence")
        val label = intent.getStringExtra("label")

        val uji = label?.split(" ")

        if (uji?.get(0)?.equals("Fresh") == true) {
            binding.summary.text = "Your food is safe to consume!"
        } else {
            binding.summary.text = "Sorry, your food is not suitable for consumption."
        }

        binding.confidence.text = "Confidence prediction: $confidence%"
        if (uji != null) {
            binding.label.text = "Condition: $label"
        }

        binding.back.setOnClickListener {
            val intentScan = Intent(this, ScanActivity::class.java)
            startActivity(intentScan)
            finish()
        }

//        binding.give.setOnClickListener {
//            val intentScan = Intent(this, ScanActivity::class.java)
//            startActivity(intentScan)
//        }

    }
}