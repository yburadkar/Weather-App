package com.yb.openweather.models

import android.os.Parcelable
import com.yb.openweather.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val id: Int,
    val name: String,
    val temp: String,
    val description: String,
    val speed: String,
    val direction: Float
) : Parcelable {

    val windIcon: Int get() = when(direction % 360) {
            in 0F..11.25F -> R.drawable.ic_direction_n
            in 11.25F..33.75F -> R.drawable.ic_direction_nne
            in 33.75F..56.25F -> R.drawable.ic_direction_ne
            in 56.25F..78.75F -> R.drawable.ic_direction_ene
            in 78.75F..101.25F -> R.drawable.ic_direction_e
            in 101.25F..123.75F -> R.drawable.ic_direction_ese
            in 123.75F..146.25F -> R.drawable.ic_direction_se
            in 146.25F..168.75F -> R.drawable.ic_direction_sse
            in 168.75F..191.25F -> R.drawable.ic_direction_s
            in 191.25F..213.75F -> R.drawable.ic_direction_ssw
            in 213.75F..236.25F -> R.drawable.ic_direction_sw
            in 236.25F..258.75F -> R.drawable.ic_direction_wsw
            in 258.75F..281.25F -> R.drawable.ic_direction_w
            in 281.25F..303.75F -> R.drawable.ic_direction_wnw
            in 303.75F..326.25F -> R.drawable.ic_direction_nw
            in 326.25F..348.75F -> R.drawable.ic_direction_nnw
            in 348.75F..360F -> R.drawable.ic_direction_n
            else -> R.drawable.ic_direction_nne
        }

}