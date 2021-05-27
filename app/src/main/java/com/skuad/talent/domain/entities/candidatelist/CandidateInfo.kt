package com.skuad.talent.domain.entities.candidatelist

data class CandidateInfo(

//    val attribution: Attribution,
    val contact_info: ContactInfo?,
//    val education: List<Education>,
    val experience: List<Experience>? = null,
    val preferences: Preferences?=null,
//    val resume: String?,
    val role_id: RoleId? = null,
    val skills: List<String>? = emptyList(),
//    val stage: String,
//    val talent_advisor: TalentAdvisor,
//    val timeline: String,
    val uid: String
)