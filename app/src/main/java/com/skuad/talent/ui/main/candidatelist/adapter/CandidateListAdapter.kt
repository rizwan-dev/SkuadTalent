package com.skuad.talent.ui.main.candidatelist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.data.model.Candidate
import com.skuad.talent.databinding.ItemCandidateListBinding
import com.skuad.talent.ui.main.candidatelist.vh.CandidateListViewHolder

class CandidateListAdapter(
    private val context: Context,
    private val candidateList: List<Candidate>,
    private val onItemClick: (Candidate) -> Unit
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
        val candidate = candidateList[position]
        holder.bind(candidate)

    }

    override fun getItemCount(): Int {
        return candidateList.size
    }

}
