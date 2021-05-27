package com.skuad.talent.ui.main.candidatelist.vh

import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.R
import com.skuad.talent.databinding.ItemCandidateListBinding
import com.skuad.talent.domain.entities.candidatelist.CandidateInfo
import timber.log.Timber

class CandidateListViewHolder(
    private val binding: ItemCandidateListBinding,
    private val onItemClick: (CandidateInfo) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    lateinit var candidate: CandidateInfo

    init {
        binding.root.setOnClickListener {
            onItemClick.invoke(candidate)
        }
    }

    fun bind(candidate: CandidateInfo) {
        this.candidate = candidate
        with(binding) {
            //name
            tvCandidateName.text = candidate.contact_info?.name
            //address
            if (!candidate.contact_info?.address.isNullOrEmpty()) {
                tvAddress.text = candidate.contact_info?.address
            } else {
                tvAddress.text = "Address : NA"
            }
            if (!candidate.experience.isNullOrEmpty()) {
                val role = candidate.experience[0].role
                //val role = candidate.role_id?.name
                val experience = candidate.experience[0].experience
                val employer = candidate.experience[0].company_id
                val roleString = if (role.isNullOrEmpty()) "Designation : NA" else role

                val experienceString =
                    if (experience.isNullOrEmpty()) "Experience : NA" else experience
                tvYearsOfExperience.text = "$roleString | $experienceString" + " years"
                val employerString =
                    if (employer.isNullOrEmpty()) "Current Employer : NA" else employer
                tvCurrentEmployer.text = employerString

            } else {
                tvYearsOfExperience.text = "Designation : NA | " + "Experience : NA"
            }

            if (!candidate.experience.isNullOrEmpty()
                && !candidate.experience[0].salary?.currency.isNullOrEmpty()
                && !candidate.experience[0].salary?.amount?.toString().isNullOrEmpty()
            ) {
                val currency = candidate.experience[0].salary?.currency
                val amount = candidate.experience[0].salary?.amount?.toInt()

                tvSalary.text = currency + " " + amount

            } else {
                tvSalary.text = "Salary : NA"
            }
            //

            if (candidate.preferences?.notice_period?.toString().isNullOrEmpty()) {
                tvNoticePeriod.text = "Notice Period : NA"
            } else {
                tvNoticePeriod.text =
                    candidate.preferences?.notice_period?.toInt().toString() + " days"
            }
        }
    }
}
