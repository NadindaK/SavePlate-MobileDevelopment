package com.dicoding.saveplate.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.saveplate.MainViewModel
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.ui.landing.LandingViewModel
import com.dicoding.saveplate.ui.login.LoginViewModel
import com.dicoding.saveplate.ui.profile.ProfileViewModel
import com.dicoding.saveplate.ui.register.RegisterViewModel
import com.dicoding.saveplate.ui.scan.ScanViewModel

class ViewModelFactory (private val pref: UserPreference, private val context: Context) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }

            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(pref) as T
            }

            modelClass.isAssignableFrom(LandingViewModel::class.java) -> {
                LandingViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }


}