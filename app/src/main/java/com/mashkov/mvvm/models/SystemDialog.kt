package com.mashkov.mvvm.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class SystemDialog(
    val title: String?,
    val message: String?,
    val positiveButtonTitle: String?,
    val negativeButtonTitle: String? = null,
    val cancelable: Boolean = false
) : Parcelable
