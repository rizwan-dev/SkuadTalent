package com.skuad.talent.ui.main.dashboard.vh

import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.data.model.Card
import com.skuad.talent.databinding.ItemDashbordCardsBinding

class DashboardViewHolder(private val binding: ItemDashbordCardsBinding, private val onItemClick: (Card) -> Unit) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var card: Card
    init {
        binding.root.setOnClickListener {
            onItemClick.invoke(card)
        }
    }

    fun bind(card: Card) {
        this.card = card
        with(binding){
            tvCardName.text = card.title
            tvActiveProfiles.text = card.active
            ivCardLogo.setImageResource(card.image)
        }
    }
}