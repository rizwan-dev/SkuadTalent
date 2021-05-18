package com.skuad.talent.ui.main.dashboard.vh

import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.R
import com.skuad.talent.databinding.ItemDashbordCardsBinding
import com.skuad.talent.domain.entities.dashboard.SkillsInfo
import kotlinx.android.synthetic.main.item_dashbord_cards.view.*
import timber.log.Timber

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

            val skillImage = when (card.name) {
                "Android Developer" -> R.drawable.android_128
                "iOS Developer" -> R.drawable.apple_logo
                "Project Manager" -> R.drawable.ic_projectmanager
                "Product Manager" -> R.drawable.ic_product_manager
                "Full Stack Developer" -> R.drawable.fullstack
                "Talent Acquisition" -> R.drawable.talent_acquisition
                "Backend developer" -> R.drawable.backend
                "PPC Analyst" -> R.drawable.ic_php
                "DevOps" -> R.drawable.devops
                "Hybrid App Developer" -> R.drawable.fullstack
                "Quality Assurance Engineer" -> R.drawable.quality
                "Sales Professional" -> R.drawable.sales
                "Graphic Designer" -> R.drawable.designer
                "UI/UX Designer" -> R.drawable.ic_uidesigner
                else -> R.drawable.backend
            }
            Timber.e("selected image resource --->" + skillImage)
            ivCardLogo.setImageResource(skillImage)
        }
    }
}