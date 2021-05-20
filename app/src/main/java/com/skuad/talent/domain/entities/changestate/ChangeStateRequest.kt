package com.skuad.talent.domain.entities.changestate

import com.squareup.moshi.Json

data class ChangeStateRequest(@Json(name = "candidate_uid") val candidateUid: String, val stage: String)
