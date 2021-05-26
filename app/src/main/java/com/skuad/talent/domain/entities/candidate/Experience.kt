package com.skuad.talent.domain.entities.candidate

import com.skuad.talent.domain.entities.candidatelist.Salary

data class Experience(
    val experience: String? = null,
    val company_id: String? = null,
   // val role: String? = null,
    val salary: Salary?
)
