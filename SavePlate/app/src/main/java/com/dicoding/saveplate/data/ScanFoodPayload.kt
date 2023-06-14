package com.dicoding.saveplate.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScanFoodPayload(
    var image :  String
) : Parcelable
