package com.skuad.talent.ui.main.dashboard.vh

import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.data.model.Card
import com.skuad.talent.databinding.ItemDashbordCardsBinding
import com.skuad.talent.domain.entities.SkillsInfo

class DashboardViewHolder(
    private val binding: ItemDashbordCardsBinding,
    private val onItemClick: (SkillsInfo) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var card: SkillsInfo

    init {
        binding.root.setOnClickListener {
            onItemClick.invoke(card)
        }
    }

    fun bind(card: SkillsInfo) {
        this.card = card
        with(binding) {
            tvCardName.text = card.name
            tvActiveProfiles.text = card.stageCount.registered.toString()
            // ivCardLogo.setImageResource(card.image)
        }
    }
}