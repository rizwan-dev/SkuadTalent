package com.skuad.talent.ui.main.candidatelist.vh

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.R
import com.skuad.talent.databinding.ItemCandidateListBinding
import com.skuad.talent.domain.entities.candidatelist.CandidateInfo
import timber.log.Timber

class CandidateListViewHolder(
    private val context: Context,
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
            val addressString = candidate.contact_info?.address
            tvAddress.text = if (addressString.isNullOrEmpty()) "Address : NA" else addressString
            if (!candidate.experience.isNullOrEmpty()) {
            val role = candidate.experience[0].role
                //val role = candidate.role_id?.name
                val experience = candidate.experience[0].experience
                val employer = candidate.experience[0].company_id
                val roleString = if (role.isNullOrEmpty()) "Designation : NA" else role

                val experienceString =
                    if (experience.isNullOrEmpty()) "Experience : NA" else experience
                tvYearsOfExperience.text = "$roleString | $experienceString years"
                val employerString =
                    if (employer.isNullOrEmpty()) "Current Employer : NA" else employer
                tvCurrentEmployer.text = employerString

            } else {
                tvYearsOfExperience.text =
                    context.getString(R.string.designation_experience_not_available)
            }

            if (!candidate.experience.isNullOrEmpty()
                && !candidate.experience[0].salary?.currency.isNullOrEmpty()
                && !candidate.experience[0].salary?.amount?.toString().isNullOrEmpty()
            ) {
                val currency = candidate.experience[0].salary?.currency
                val amount = candidate.experience[0].salary?.amount?.toInt()


                val s: String = amount.toString()
                val d = java.lang.Double.valueOf(s)
                val amountWithComma = String.format("%,.0f", d)
                tvSalary.text = "₹ $amountWithComma"

            } else {
                tvSalary.text = context.getString(R.string.salary_not_available)
            }
            //

            if (candidate.preferences?.notice_period?.toString().isNullOrEmpty()) {
                tvNoticePeriod.text = context.getString(R.string.notice_period_not_available)
            } else {
                tvNoticePeriod.text =
                    candidate.preferences?.notice_period?.toInt().toString() + " days"
            }

        }
    }
}
