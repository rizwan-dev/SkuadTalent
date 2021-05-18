package com.skuad.talent.domain.repository

import com.skuad.talent.base.BaseRepo
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.candidate.CandidateDetailsRequest
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin

interface CandidateRepo : BaseRepo {
    suspend fun getCandidateDetails(candidateDetailsRequest: CandidateDetailsRequest) : ResourceState<GetCandidateByAdmin>
}