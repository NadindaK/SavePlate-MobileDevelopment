package com.dicoding.saveplate.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterPayload (
    var  username :  String,
    var email :  String,
    var password :  String,
    var confPassword :  String
) : Parcelable