package com.skuad.talent.domain.entities.candidatelist

data class MetaInfo(
    val online_profile_data: OnlineProfileData,
    val personal_details: PersonalDetails,
    val work_authorization: WorkAuthorization,
    val work_summary: WorkSummary
)