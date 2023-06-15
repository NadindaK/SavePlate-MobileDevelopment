package com.dicoding.saveplate.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recycle (
    val title: String,
    val source : String,
    val photo: Int,
    val article: String,
) : Parcelable
