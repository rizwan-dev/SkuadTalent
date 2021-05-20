package com.skuad.talent.data.mapper.changestate

import com.skuad.talent.ChangeStateCandidateMutation
import com.skuad.talent.data.mapper.ResponseMapper
import com.skuad.talent.domain.entities.changestate.ChangeStateResponse

class ChangeStateMapper : ResponseMapper<ChangeStateCandidateMutation.Data, ChangeStateResponse>{

    override fun map(input: ChangeStateCandidateMutation.Data): ChangeStateResponse {
        return mapToChangeState(input.changeStateCandidate())
    }

    private fun mapToChangeState(changeStateCandidate: ChangeStateCandidateMutation.ChangeStateCandidate): ChangeStateResponse {
        return changeStateCandidate.run { ChangeStateResponse(candidate_uid() ?: "", stage() ?: "" ) }
    }

}