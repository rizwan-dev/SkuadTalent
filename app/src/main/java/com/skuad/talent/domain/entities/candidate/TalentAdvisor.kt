package com.skuad.talent.domain.entities.candidate

data class TalentAdvisor(
    val __typename: String,
    val recruiter_id: RecruiterId,
    val sourcer_id: SourcerId
)