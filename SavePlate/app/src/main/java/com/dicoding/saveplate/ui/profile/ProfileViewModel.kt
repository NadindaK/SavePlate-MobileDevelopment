package com.dicoding.saveplate.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.saveplate.data.User
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.response.Data
import com.dicoding.saveplate.response.LoginResponse
import com.dicoding.saveplate.response.ProfileResponse
import com.dicoding.saveplate.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val pref: UserPreference) : ViewModel()  {

    private val _user = MutableLiveData<LoginResponse>()

    private val _profile = MutableLiveData<Data>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun getProfile(token : String, jwtToken : String) : LiveData<Data>  {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getProfile(token, jwtToken)
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _profile.value = response.body()?.data
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return _profile
    }

    companion object{
        private const val TAG = "ProfileViewModel"
    }





}