package com.skuad.talent.data.mapper.candidatelist

import com.skuad.talent.GetCandidatesByRoleQuery
import com.skuad.talent.data.mapper.ResponseMapper
import com.skuad.talent.domain.entities.candidatelist.*
import org.jetbrains.annotations.Nullable


class CandidateListMapper : ResponseMapper<GetCandidatesByRoleQuery.Data, List<CandidateInfo>> {
    override fun map(input: GetCandidatesByRoleQuery.Data) =
        mapToCandidateList(input.candidatesByRole)

    private fun mapToCandidateList(candidatesByRole: List<GetCandidatesByRoleQuery.GetCandidatesByRole>): List<CandidateInfo> {
        return candidatesByRole.map {
            CandidateInfo(
                uid = it.uid() ?: "",
                contact_info = getContactInfo(it.contact_info()),
                skills = it.skills() ?: emptyList(),
                experience = getExperience(it.experience()),
                preferences = getPreferences(it.preferences()),
                role_id = getRollId(it.role_id())
            )
        }
    }

    private fun getRollId(roleId: GetCandidatesByRoleQuery.Role_id?): RoleId? {
        return roleId?.run {
            RoleId(name = name() ?: "")
        }
    }

    private fun getPreferences(preferences: GetCandidatesByRoleQuery.Preferences?): Preferences? {
        return preferences?.run {
            Preferences(notice_period = notice_period() ?: "")
        }
    }


    private fun getExperience(experience: List<GetCandidatesByRoleQuery.Experience>?): List<Experience>? {

        return experience?.map {
            Experience(
                experience = it.experience(),
                company_id = it.company_id(),
                salary = mapToSalary(it.salary()),
                role = it.role()
            )

        }
    }

    private fun mapToSalary(salary: GetCandidatesByRoleQuery.Salary?): Salary? {
        return salary?.run {
            Salary(
                currency = currency() ?: ""
            )
        }

    }

    private fun getContactInfo(contactInfo: GetCandidatesByRoleQuery.Contact_info?): ContactInfo? {
        return contactInfo?.run { ContactInfo(name = name() ?: "", address = address() ?: "") }
    }
}