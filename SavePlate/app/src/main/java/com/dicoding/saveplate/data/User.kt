package com.dicoding.saveplate.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val isLogin: Boolean,
    val token: String,
) : Parcelable