package com.skuad.talent.domain.entities.candidatelist

import org.jetbrains.annotations.Nullable

data class Preferences(
//    val __typename: String,
    // val expected_hike: Any,
    // val expected_salary: Any,
    // val expected_salary_currency: Any,
    // val hourly_rate: Any,
    //  val job_type: List<String>,
    //  val location_type: List<Any>,
    //   val locations: List<String>,
    val notice_period: @Nullable Double?
)