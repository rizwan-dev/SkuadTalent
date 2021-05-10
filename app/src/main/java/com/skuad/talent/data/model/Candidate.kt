package com.skuad.talent.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Candidate(val candidateName: String, val experience: String, val skills: String) :Parcelable
