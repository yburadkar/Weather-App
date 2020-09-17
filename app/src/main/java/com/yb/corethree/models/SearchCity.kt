package com.yb.corethree.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val id: Int,
    val name: String,
    val temp: String,
    val description: String,
    val speed: String,
    val direction: Float
) : Parcelable