package com.skuad.talent.data.mapper.candidatedetails

import com.skuad.talent.GetCandidateByAdminQuery
import com.skuad.talent.data.mapper.ResponseMapper
import com.skuad.talent.domain.entities.candidate.ContactInfo
import com.skuad.talent.domain.entities.candidate.Experience
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin

class CandidateDetailsMapper : ResponseMapper<GetCandidateByAdminQuery.Data, GetCandidateByAdmin> {

    override fun map(input: GetCandidateByAdminQuery.Data): GetCandidateByAdmin {
        return mapToCandidate(input.candidateByAdmin)
    }

    private fun mapToCandidate(candidateByAdmin: GetCandidateByAdminQuery.GetCandidateByAdmin?): GetCandidateByAdmin {
        return candidateByAdmin?.run {
            GetCandidateByAdmin(
                experience = getExperience(experience()),
                resume = resume(),
                contact_info = getContactInfo(contact_info()),
                uid = uid() ?: "",
                skills = skills().toString()

            )
        } ?: kotlin.run { GetCandidateByAdmin() }
    }


    private fun getContactInfo(contactInfo: GetCandidateByAdminQuery.Contact_info?): ContactInfo? {
        return contactInfo?.run {
            ContactInfo(name() ?: "")
        }
    }

    private fun getExperience(experience: List<GetCandidateByAdminQuery.Experience>?): List<Experience>? {
        return experience?.map {
            Experience(it.experience())
        }
    }


}