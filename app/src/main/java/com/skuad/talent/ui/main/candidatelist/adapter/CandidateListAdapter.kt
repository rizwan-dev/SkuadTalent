package com.skuad.talent.ui.main.candidatelist.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.data.model.Candidate
import com.skuad.talent.databinding.ItemCandidateListBinding
import com.skuad.talent.domain.entities.candidatelist.CandidateInfo
import com.skuad.talent.ui.main.candidatelist.vh.CandidateListViewHolder

class CandidateListAdapter(
    private val context: Context,
    private val candidateList: List<CandidateInfo>,
    private val onItemClick: (CandidateInfo) -> Unit
) :
    RecyclerView.Adapter<CandidateListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CandidateListViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemCandidateListBinding.inflate(inflater, parent, false)
        return CandidateListViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CandidateListViewHolder, position: Int) {
        val candidateInfo = candidateList[position]
        holder.bind(candidateInfo)

    }

    override fun getItemCount(): Int {
        return candidateList.size
    }

}
