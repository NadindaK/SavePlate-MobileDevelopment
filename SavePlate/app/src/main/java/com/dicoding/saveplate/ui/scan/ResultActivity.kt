package com.dicoding.saveplate.ui.scan

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.saveplate.MainActivity
import com.dicoding.saveplate.R
import com.dicoding.saveplate.databinding.ActivityResultBinding
import com.dicoding.saveplate.ui.donateLoc.DonateLocActivity
import com.dicoding.saveplate.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.dicoding.saveplate.ui.recycle.RecycleActivity
import org.json.JSONObject


class ResultActivity : AppCompatActivity() {

    private var _binding: ActivityResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Result"

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#44746D")))

        bottomNavigationView = binding.navView

        bottomNavigationView.selectedItemId = R.id.scan

        bottomNavigationView.setOnItemSelectedListener { item ->
            val intentHome = Intent(this, MainActivity::class.java)
            val intentProfile = Intent(this, ProfileActivity::class.java)
            when (item.itemId) {
                R.id.home -> {
                    startActivity(intentHome)
                    true}
                R.id.scan -> {
                    true}
                R.id.profile -> {
                    startActivity(intentProfile)
                    true}
            }
            false
        }

        val confidence = intent.getStringExtra("confidence")
        val label = intent.getStringExtra("label")

        val uji = label?.split(" ")

        if (uji?.get(0)?.equals("Fresh") == true) {
            binding.summary.text = "Your food is safe to consume! Let's discover a place to donate your food by clicking the Donate button"
            binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.check))
        } else {
            binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.cross))
            binding.summary.text = "Sorry, your food is not suitable for consumption. Let's find a way to process it by clicking the Recycle button"
        }

        binding.confidence.text = "Confidence prediction: $confidence%"
        if (uji != null) {
            binding.label.text = "Condition: $label"
        }

        binding.back.setOnClickListener {
            val intentRecycle = Intent(this, RecycleActivity::class.java)
            startActivity(intentRecycle)
        }

        binding.give.setOnClickListener {
            val intentDonate = Intent(this, DonateLocActivity::class.java)
            startActivity(intentDonate)
        }

    }


}