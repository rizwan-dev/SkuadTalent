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
                if (!candidate.role_id?.name.isNullOrEmpty()) {
                    val designation = candidate.role_id?.name
                    tvYearsOfExperience.text =
                        "$designation | " + (candidate.experience[0].experience
                            ?: "").plus(" years")
                } else {
                    val designation = "Designation : NA"
                    tvYearsOfExperience.text =
                        "$designation | " + (candidate.experience[0].experience
                            ?: "").plus(" years")
                }


            } else {
                tvYearsOfExperience.text = "Designation : NA | " + "Experience : NA"
            }
            //
            if (!candidate.experience.isNullOrEmpty() && !candidate.experience[0].company_id.isNullOrEmpty()

            ) {

                tvCurrentEmployer.text = candidate.experience[0].company_id

            } else {
                tvCurrentEmployer.text = "Current Employer : NA"
            }
            //
            if (!candidate.experience.isNullOrEmpty()
                && !candidate.experience[0].salary?.currency.isNullOrEmpty()
                && !candidate.experience[0].salary?.amount?.toDouble().toString().isNullOrEmpty()
            ) {

                tvSalary.text =
                    "${candidate.experience[0].salary?.currency }${candidate.experience[0].salary?.amount?.toDouble().toString()}"
                Timber.e("in if statement --->" + tvSalary.text)
            } else {
                tvSalary.text = "Salary : NA"
            }
            //

            if (candidate.preferences?.notice_period?.toDouble().toString().isNullOrEmpty()) {
                tvNoticePeriod.text = "Notice Period : NA"
            } else {
                tvNoticePeriod.text = candidate.preferences?.notice_period?.toDouble().toString()
            }
        }
    }
}
