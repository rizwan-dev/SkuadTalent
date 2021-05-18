package com.skuad.talent.data.repository

import com.apollographql.apollo.ApolloClient
import com.skuad.talent.GetCandidateByAdminQuery
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.base.extensions.mapToEntity
import com.skuad.talent.data.mapper.candidate.CandidateDetailsMapper
import com.skuad.talent.domain.entities.candidate.CandidateDetailsRequest
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.domain.repository.CandidateRepo

class CandidateRepoImpl(private val apolloClient: ApolloClient) : CandidateRepo {
    override suspend fun getCandidateDetails(candidateDetailsRequest: CandidateDetailsRequest): ResourceState<GetCandidateByAdmin> {
        return apolloClient.query(GetCandidateByAdminQuery.builder().id(candidateDetailsRequest.id).requirement_id(candidateDetailsRequest.requirementId).build()).mapToEntity {
            CandidateDetailsMapper().map(it)
        }
    }
}