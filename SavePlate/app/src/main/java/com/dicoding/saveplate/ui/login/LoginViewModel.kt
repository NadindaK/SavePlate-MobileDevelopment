package com.dicoding.saveplate.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.saveplate.data.User
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.response.LoginResponse
import com.dicoding.saveplate.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel()  {

    private val _user = MutableLiveData<LoginResponse>()

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

    fun postLogin(email: String, password: String)  : LiveData<LoginResponse> {
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return _user
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }

}