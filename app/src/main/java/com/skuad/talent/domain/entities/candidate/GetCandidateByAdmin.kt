package com.skuad.talent.domain.entities.candidate

data class GetCandidateByAdmin(
    val assessments: List<Any>,
    val attribution: Attribution,
    val contact_info: ContactInfo,
    val ctags: List<String>,
    val education: List<Any>,
    val experience: List<Any>,
    val linkedin_profile: String,
    val metaInfo: MetaInfo,
    val preferences: Preferences,
    val resume: String,
    val role_id: RoleId,
    val skills: List<String>,
    val stage: String,
    val talent_advisor: TalentAdvisor,
    val timeline: String,
    val uid: String
)