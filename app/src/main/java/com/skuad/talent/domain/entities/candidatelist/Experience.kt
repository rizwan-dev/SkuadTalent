package com.skuad.talent.domain.entities.candidatelist

data class Experience(

    val company_id: String?=null,
    //val current: Boolean,
    val experience: String? = null,
    // val freelancer: Any,
    // val from_date: String,
    //  val location: String,
    val role: String? = null,
    val salary: Salary?

    //  val to_date: Any
)