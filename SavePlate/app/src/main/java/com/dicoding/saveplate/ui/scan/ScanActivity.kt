package com.dicoding.saveplate.ui.scan

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.saveplate.R
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.databinding.ActivityProfileBinding
import com.dicoding.saveplate.databinding.ActivityScanBinding
import com.dicoding.saveplate.ui.ViewModelFactory
import com.dicoding.saveplate.ui.profile.ProfileViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ScanActivity : AppCompatActivity() {
        private lateinit var scanViewModel: ScanViewModel
        private lateinit var binding: ActivityScanBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityScanBinding.inflate(layoutInflater)
            setContentView(binding.root)

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
                title = getString(R.string.scan)
            }

        }

        private fun setupViewModel(){
            scanViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore), this)
            )[ScanViewModel::class.java]
        }
}