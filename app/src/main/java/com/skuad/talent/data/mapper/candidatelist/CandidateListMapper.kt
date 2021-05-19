package com.skuad.talent.data.mapper.candidatelist

import com.skuad.talent.GetCandidatesByRoleQuery
import com.skuad.talent.data.mapper.ResponseMapper
import com.skuad.talent.domain.entities.candidatelist.CandidateInfo
import com.skuad.talent.domain.entities.candidatelist.ContactInfo
import com.skuad.talent.domain.entities.candidatelist.Experience


class CandidateListMapper : ResponseMapper<GetCandidatesByRoleQuery.Data, List<CandidateInfo>> {
    override fun map(input: GetCandidatesByRoleQuery.Data) =
        mapToCandidateList(input.candidatesByRole)

    private fun mapToCandidateList(candidatesByRole: List<GetCandidatesByRoleQuery.GetCandidatesByRole>): List<CandidateInfo> {
        return candidatesByRole.map {
            CandidateInfo(
                uid = it.uid() ?: "",
                contact_info = getContactInfo(it.contact_info()),
                skills = it.skills().toString(),
//                experience = getExperience(it.experience())
            )
        }

    }

//    private fun getExperience(experience: List<GetCandidatesByRoleQuery.Experience>?): Experience? {
//        return experience?.run {
//            Experience(
//                experience = experience() ?: "",
//            role = role()?:"") }
//    }


    private fun getContactInfo(contactInfo: GetCandidatesByRoleQuery.Contact_info?): ContactInfo? {
        return contactInfo?.run { ContactInfo(name = name() ?: "") }
    }
}