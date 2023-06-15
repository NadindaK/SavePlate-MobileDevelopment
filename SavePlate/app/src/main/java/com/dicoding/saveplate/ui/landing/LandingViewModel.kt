package com.dicoding.saveplate.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.saveplate.data.User
import com.dicoding.saveplate.data.UserPreference

class LandingViewModel(private val pref: UserPreference) : ViewModel()  {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}