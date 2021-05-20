package com.skuad.talent.domain.repository

import com.skuad.talent.base.BaseRepo
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.candidate.CandidateDetailsRequest
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.domain.entities.candidatelist.CandidateInfo
import com.skuad.talent.domain.entities.candidatelist.CandidateListRequest
import com.skuad.talent.domain.entities.changestate.ChangeStateRequest
import com.skuad.talent.domain.entities.changestate.ChangeStateResponse

interface CandidateRepo : BaseRepo {
    suspend fun getCandidateDetails(candidateDetailsRequest: CandidateDetailsRequest) : ResourceState<GetCandidateByAdmin>

    suspend fun getCandidateList(candidateListRequest: CandidateListRequest) : ResourceState<List<CandidateInfo>>

    suspend fun changeState(changeStateRequest: ChangeStateRequest) : ResourceState<ChangeStateResponse>
}