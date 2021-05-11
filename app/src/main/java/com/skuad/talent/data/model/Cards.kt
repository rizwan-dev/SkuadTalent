package com.skuad.talent.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cards(val imageCard: Int, val titleCard: String, val activeCard: String):Parcelable