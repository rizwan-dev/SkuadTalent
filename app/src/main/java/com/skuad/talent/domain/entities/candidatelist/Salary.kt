package com.skuad.talent.domain.entities.candidatelist

import org.jetbrains.annotations.Nullable

data class Salary(
    val amount: @Nullable Double?,
    val currency: String?=null
)