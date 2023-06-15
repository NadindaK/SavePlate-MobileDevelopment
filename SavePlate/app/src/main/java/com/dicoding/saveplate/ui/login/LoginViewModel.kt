package com.dicoding.saveplate.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.saveplate.data.LoginPayload
import com.dicoding.saveplate.data.User
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.response.LoginResponse
import com.dicoding.saveplate.retrofit.ApiConfig
import com.dicoding.saveplate.ui.Event
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel()  {

    private val _user = MutableLiveData<LoginResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun login(token : String) {
        viewModelScope.launch {
            pref.login(token)
        }
    }

    fun postLogin(email: String, password: String)  : LiveData<LoginResponse> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(LoginPayload( email, password))
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
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
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return _user
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }

}