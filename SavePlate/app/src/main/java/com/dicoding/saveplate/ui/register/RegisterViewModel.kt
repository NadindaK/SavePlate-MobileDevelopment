package com.dicoding.saveplate.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.saveplate.data.RegisterPayload
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.response.RegisterResponse
import com.dicoding.saveplate.retrofit.ApiConfig
import com.dicoding.saveplate.ui.Event
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {
    private val _user = MutableLiveData<RegisterResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun postRegister(username: String, email: String, password: String, confPassword : String)  : LiveData<RegisterResponse> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(RegisterPayload(username, email, password, confPassword))
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    val result = response.errorBody()?.string()?.let { JSONObject(it) }
                    if (result != null) {
                        _snackbarText.value = Event(result.getString("message"))
                    }
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return _user
    }

    companion object{
        private const val TAG = "RegisterViewModel"
    }

}