package com.dicoding.saveplate.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profil"

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

    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[ProfileViewModel::class.java]

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
        Glide.with(this).load(profile.pic).into(binding.imageView)
        binding.tvName.text = profile.username
        binding.tvEmail.text = profile.email

    }



}