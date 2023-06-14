package com.dicoding.saveplate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.saveplate.data.User
import com.dicoding.saveplate.data.UserPreference
import kotlinx.coroutines.launch

class MainViewModel (private val pref: UserPreference) : ViewModel(){
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    companion object{
        private const val TAG = "MainViewModel"
    }

}