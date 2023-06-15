package com.dicoding.saveplate.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.saveplate.R
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.databinding.ActivityProfileBinding
import com.dicoding.saveplate.response.Data
import com.dicoding.saveplate.ui.ViewModelFactory
import com.dicoding.saveplate.ui.landing.LandingActivity
import com.bumptech.glide.Glide
import com.dicoding.saveplate.MainActivity
import com.dicoding.saveplate.ui.scan.ScanActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: ActivityProfileBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profil"


        bottomNavigationView = binding.navView

        bottomNavigationView.selectedItemId = R.id.profile

        bottomNavigationView.setOnItemSelectedListener { item ->
            val intentHome = Intent(this, MainActivity::class.java)
            val intentScan = Intent(this, ScanActivity::class.java)
            when (item.itemId) {
                R.id.home -> {
                    startActivity(intentHome)
                    true}
                R.id.scan -> {
                    startActivity(intentScan)
                    true}
                R.id.profile -> {
                    true}
            }
            false
        }

        setupView()
        setupViewModel()

    }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.apply {
            title = getString(R.string.profile)
        }

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#44746D")))

    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[ProfileViewModel::class.java]

        profileViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        profileViewModel.getUser().observe(this) { user ->
            if (!user.isLogin){
                startActivity(Intent(this, LandingActivity::class.java))
                finish()
            } else {
                val userToken = "Bearer " + user.token
                val userJwtToken = "jwt=" + user.token
                Log.d("tagDebug", userToken)
                profileViewModel.getProfile(userToken, userJwtToken).observe(this) { profile ->
                    showProfile(profile)
                }

            }

        }

    }

    private fun showProfile(profile: Data){
        Glide.with(this).load(profile.pic).circleCrop().override(500 , 500).into(binding.imageView)
        binding.tvName.text = profile.username
        binding.tvEmail.text = profile.email
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }







}