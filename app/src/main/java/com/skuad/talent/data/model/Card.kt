package com.skuad.talent.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(val image: Int, val title: String, val active: String) : Parcelable