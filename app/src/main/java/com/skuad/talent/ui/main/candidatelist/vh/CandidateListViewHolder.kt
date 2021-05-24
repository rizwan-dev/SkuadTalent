package com.skuad.talent.ui.main.candidatelist.vh

import androidx.recyclerview.widget.RecyclerView
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
            tvCandidateName.text = candidate.contact_info?.name



            if (!candidate.experience.isNullOrEmpty()) {
                Timber.e(
                    "Address---${candidate.experience[0].experience}Current Employer--${candidate.experience[0].company_id}Salary--${candidate.experience[0].salary?.currency}"
                )

                if (!candidate.experience[0].role.isNullOrEmpty()) {
                    val designation = candidate.experience[0].role
                    tvYearsOfExperience.text =
                        "$designation | ${(candidate.experience[0].experience ?: "").plus(" years")}"
                } else {
                    val designation = "Designation : NA"
                }
                if (!candidate.experience[0].company_id.isNullOrEmpty()) {
                    tvCurrentEmployer.text = candidate.experience[0].company_id ?: ""
                } else {
                    tvCurrentEmployer.text = "CurrentEmployer : NA"
                }
                if (!candidate.experience[0].salary?.currency.isNullOrEmpty()) {
                    tvSalary.text = candidate.experience[0].salary?.currency
                } else {
                    tvSalary.text = "Salary : NA"
                }
            } else {
                tvYearsOfExperience.text = "Experience : NA"
            }
            if (!candidate.contact_info?.address.isNullOrEmpty()) {
                Timber.e("Address---${candidate.contact_info?.address}")
                tvAddress.text = candidate.contact_info?.address
                // tvAddress.text = candidate.skills.joinToString(", ")
            } else {
                tvAddress.text = "Address : NA"
            }
            if (candidate.preferences?.notice_period.toString().isNullOrEmpty()) {
                tvNoticePeriod.text = "Notice Period : NA"
            } else {
                tvNoticePeriod.text = candidate.preferences?.notice_period.toString()
            }
        }
    }
}
