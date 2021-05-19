package com.skuad.talent.domain.entities.candidatelist

data class Experience(
    val __typename: String,
    val company_id: String,
    val current: Boolean,
    val experience: String,
    val freelancer: Any,
    val from_date: String,
    val location: String,
    val role: String,
    val salary: Salary,
    val to_date: Any
)