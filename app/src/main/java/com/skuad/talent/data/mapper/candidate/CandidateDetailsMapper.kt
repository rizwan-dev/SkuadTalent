package com.skuad.talent.data.mapper.candidate

import com.skuad.talent.GetCandidateByAdminQuery
import com.skuad.talent.data.mapper.ResponseMapper
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin

class CandidateDetailsMapper : ResponseMapper<GetCandidateByAdminQuery.Data, GetCandidateByAdmin> {

    override fun map(input: GetCandidateByAdminQuery.Data): GetCandidateByAdmin {
        return mapToCandidate(input.candidateByAdmin)
    }

    private fun mapToCandidate(candidateByAdmin: GetCandidateByAdminQuery.GetCandidateByAdmin?): GetCandidateByAdmin {
        return candidateByAdmin.run {
            GetCandidateByAdmin(
                experience = candidateByAdmin?.experience(),
                resume = candidateByAdmin?.resume()
            )
        }
    }


}