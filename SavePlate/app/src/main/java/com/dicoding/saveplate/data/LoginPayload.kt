package com.dicoding.saveplate.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginPayload (
    var email :  String,
    var password :  String
) : Parcelable