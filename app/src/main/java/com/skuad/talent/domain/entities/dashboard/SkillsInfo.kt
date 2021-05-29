package com.skuad.talent.domain.entities.dashboard

import com.squareup.moshi.Json

data class SkillsInfo(@Json(name = "_id") val id: String, @Json(name = "name")val name: String, val stageCount: StageCount)

data class StageCount(val sourced: Int, val hired: Int, val offered: Int)