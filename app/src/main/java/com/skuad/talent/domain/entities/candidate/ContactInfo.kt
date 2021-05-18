package com.skuad.talent.domain.entities.candidate

data class ContactInfo(
    val __typename: String,
    val address: String,
    val email: List<String>,
    val name: String,
    val phone: List<String>,
    val whatsapp_no: String
)