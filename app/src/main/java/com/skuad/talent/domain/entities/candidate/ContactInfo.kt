package com.skuad.talent.domain.entities.candidate

data class ContactInfo(
    val email: List<String> = emptyList(),
    val address: String,
    val name: String
)