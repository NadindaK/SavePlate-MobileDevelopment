package com.dicoding.saveplate.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.saveplate.data.RegisterPayload
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.response.RegisterResponse
import com.dicoding.saveplate.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {
    private val _user = MutableLiveData<RegisterResponse>()

    fun postRegister(username: String, email: String, password: String, confPassword : String)  : LiveData<RegisterResponse> {
        val client = ApiConfig.getApiService().register(RegisterPayload(username, email, password, confPassword))
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return _user
    }

    companion object{
        private const val TAG = "RegisterViewModel"
    }

}