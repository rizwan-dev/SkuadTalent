package com.skuad.talent.domain.entities.changestate

import com.squareup.moshi.Json

data class ChangeStateResponse(@Json(name = "candidate_uid") val candidateUid: String, val stage: String)
