package com.bassul.flavorfusion.presentation.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DetailViewArg(
    val id: Long,
    val name: String,
    val imageUrl: String
): Parcelable
