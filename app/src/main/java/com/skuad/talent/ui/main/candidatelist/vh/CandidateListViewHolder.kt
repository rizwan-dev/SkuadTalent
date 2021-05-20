package com.skuad.talent.ui.main.candidatelist.vh

import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.databinding.ItemCandidateListBinding
import com.skuad.talent.domain.entities.candidatelist.CandidateInfo

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
            tvYearsOfExperience.text = candidate.experience.toString()
//            tvSkills.text = candidate.skills.toString()

        }
    }

}

