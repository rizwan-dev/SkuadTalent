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
            tvAddress.text = candidate.contact_info?.address
            Timber.e("Address---${candidate.contact_info?.address}")
            if (!candidate.experience.isNullOrEmpty()) {
                tvYearsOfExperience.text = (candidate.experience[0].experience ?: "").plus(" years")

            } else {
                tvYearsOfExperience.text = "Experience : NA"
            }
            if (!candidate.contact_info?.address.isNullOrEmpty()) {
                tvAddress.text = candidate.contact_info?.address
                // tvAddress.text = candidate.skills.joinToString(", ")
            } else {
                tvAddress.text = "Skills : NA"
            }
        }
    }
}
