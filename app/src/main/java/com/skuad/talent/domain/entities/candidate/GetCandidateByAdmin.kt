package com.skuad.talent.domain.entities.candidate

data class GetCandidateByAdmin(
//    val attribution: Attribution,
    val contact_info: ContactInfo? = null,
//    val ctags: List<String>,
//    val education: List<Any>,
    val experience: List<Experience>? = null,
//    val linkedin_profile: String,
//    val metaInfo: MetaInfo,
//    val preferences: Preferences,
    val resume: String? = null,
//    val role_id: RoleId,
    val skills: List<String> = emptyList(),
//    val stage: String,
//    val talent_advisor: TalentAdvisor,
//    val timeline: String,
    val uid: String = ""
)