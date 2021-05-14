package com.skuad.talent.ui.main.candidatelist.vh

import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.data.model.Candidate
import com.skuad.talent.databinding.ItemCandidateListBinding

class CandidateListViewHolder(
    private val binding: ItemCandidateListBinding,
    private val onItemClick: (Candidate) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    lateinit var candidate: Candidate

    init {
        binding.root.setOnClickListener {
            onItemClick.invoke(candidate)
        }
    }

    fun bind(candidate: Candidate) {
        this.candidate = candidate
        with(binding) {
            tvCandidateName.text = candidate.candidateName
            tvYearsOfExperience.text = candidate.experience
            tvSkills.text = candidate.skills
        }
    }

}

