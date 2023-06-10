package com.dicoding.saveplate.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.saveplate.MainActivity
import com.dicoding.saveplate.R
import com.dicoding.saveplate.ui.landing.LandingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()). postDelayed({val splashIntent = Intent(this@SplashActivity, LandingActivity::class.java)
            startActivity(splashIntent)}, duration.toLong())
    }

    companion object{
        private const val duration = 3000
    }
}