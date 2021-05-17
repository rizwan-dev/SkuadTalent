package com.skuad.talent.ui.main.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.data.model.Card
import com.skuad.talent.databinding.ItemDashbordCardsBinding
import com.skuad.talent.domain.entities.SkillsInfo
import com.skuad.talent.ui.main.dashboard.vh.DashboardViewHolder

class DashboardAdapter(
    private val context: Context,
    private val cardList: List<SkillsInfo>,
    private val onItemClick: (SkillsInfo) -> Unit
) :
    RecyclerView.Adapter<DashboardViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemDashbordCardsBinding.inflate(inflater, parent, false)
        return DashboardViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val cards = cardList[position]
        holder.bind(cards)

    }

    override fun getItemCount(): Int {
        return cardList.size
    }



}
