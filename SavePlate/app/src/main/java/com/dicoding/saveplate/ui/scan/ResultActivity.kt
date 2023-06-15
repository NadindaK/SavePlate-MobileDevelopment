package com.dicoding.saveplate.ui.scan

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.saveplate.MainActivity
import com.dicoding.saveplate.R
import com.dicoding.saveplate.databinding.ActivityResultBinding
import com.dicoding.saveplate.ui.donateLoc.DonateLocActivity
import com.dicoding.saveplate.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject


class ResultActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

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
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val confidence = intent.getStringExtra("confidence")
        val label = intent.getStringExtra("label")

        val uji = label?.split(" ")

        if (uji?.get(0)?.equals("Fresh") == true) {
            binding.summary.text = "Your food is safe to consume!"
            binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.check))
        } else {
            binding.summary.text = "Sorry, your food is not suitable for consumption."
            binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.cross))
        }

        binding.confidence.text = "Confidence prediction: $confidence%"
        if (uji != null) {
            binding.label.text = "Condition: $label"
        }

        binding.back.setOnClickListener {
            val intentBack = Intent(this, ScanActivity::class.java)
            startActivity(intentBack)
            finish()
        }

        binding.give.setOnClickListener {
            val intentGive = Intent(this, DonateLocActivity::class.java)
            startActivity(intentGive)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val intentHome = Intent(this, MainActivity::class.java)
        val intentScan = Intent(this, ScanActivity::class.java)
        val intentProfile = Intent(this, ProfileActivity::class.java)
        when (item.itemId) {
            R.id.home -> startActivity(intentHome)
            R.id.scan -> startActivity(intentScan)
            R.id.profile -> startActivity(intentProfile)
        }
        return super.onOptionsItemSelected(item)
    }
}