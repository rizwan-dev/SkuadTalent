package com.skuad.talent.ui.main.candidatelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.R
import com.skuad.talent.data.model.Candidate
import com.skuad.talent.databinding.ActivityCandidateListBinding

class CandidateListAdapter(
    private val context: Context,
    private val candidateList: List<Candidate>,
    private val listener: (Candidate) -> Unit
) :
    RecyclerView.Adapter<CandidateListAdapter.ViewHolder>() {
    class ViewHolder(
        listItemView: View
    ) :
        RecyclerView.ViewHolder(listItemView) {
        val candidateName = itemView.findViewById<TextView>(R.id.tvCandidateName)
        val experience = itemView.findViewById<TextView>(R.id.tvYearsOfExperience)
        val skills = itemView.findViewById<TextView>(R.id.tvSkills)

        fun bind(candidate: Candidate) {
            candidateName.text = candidate.candidateName
            experience.text = candidate.experience
            skills.text = candidate.skills
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CandidateListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val candidateView = inflater.inflate(R.layout.item_candidate_list, parent, false)
        return ViewHolder(candidateView)
    }

    override fun onBindViewHolder(holder: CandidateListAdapter.ViewHolder, position: Int) {
        val candidate = candidateList.get(position)
        holder.bind(candidate)
        holder.itemView.setOnClickListener { listener(candidate) }

    }

    override fun getItemCount(): Int {
        return candidateList.size
    }

}
