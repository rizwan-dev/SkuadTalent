package com.skuad.talent.data.mapper.candidatedetails

import com.skuad.talent.GetCandidateByAdminQuery
import com.skuad.talent.data.mapper.ResponseMapper
import com.skuad.talent.domain.entities.candidate.*
import com.skuad.talent.domain.entities.candidatelist.Salary

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
                skills = skills() ?: emptyList(),
                role_id = getRoll(role_id()),
                preferences = getPreferences(preferences()),
                resumeUrl = resumeUrl()
            )
        } ?: kotlin.run { GetCandidateByAdmin() }
    }

    private fun getPreferences(preferences: GetCandidateByAdminQuery.Preferences?): Preferences? {
        return preferences?.run { Preferences(notice_period()) }
    }

    private fun getRoll(roleId: GetCandidateByAdminQuery.Role_id?): RoleId? {
        return roleId?.run { RoleId(name() ?: "") }
    }


    private fun getContactInfo(contactInfo: GetCandidateByAdminQuery.Contact_info?): ContactInfo? {
        return contactInfo?.run {
            ContactInfo(email= if(email().isNullOrEmpty()) "" else email()!![0],name = name()?:"",address = address()?:"")
        }
    }




    private fun getExperience(experience: List<GetCandidateByAdminQuery.Experience>?): List<Experience>? {
        return experience?.map {
            Experience(it.experience(), it.company_id(),it.role(), mapSalary(it.salary()))
        }
    }

    private fun mapSalary(salary: GetCandidateByAdminQuery.Salary?): Salary? {
        return salary?.run { Salary(amount(), currency() ?: "") }
    }


}