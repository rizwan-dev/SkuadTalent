package com.skuad.talent.ui.main.dashboard.vh

import androidx.recyclerview.widget.RecyclerView
import com.skuad.talent.R
import com.skuad.talent.databinding.ItemDashbordCardsBinding
import com.skuad.talent.domain.entities.dashboard.SkillsInfo
import timber.log.Timber

class DashboardViewHolder(
    private val binding: ItemDashbordCardsBinding,
    private val onItemClick: (SkillsInfo) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var skillsInfo: SkillsInfo

    init {
        binding.root.setOnClickListener {
            onItemClick.invoke(skillsInfo)
        }
    }

    fun bind(skills: SkillsInfo) {
        this.skillsInfo = skills
        with(binding) {
            tvCardName.text = skills.name
            tvActiveProfiles.text = skills.stageCount.sourced.toString()

            val skillImage = when (skills.name) {
                ANDROID_DEVELOPER -> R.drawable.android_128
                IOS_DEVELOPER -> R.drawable.apple_logo
                PROJECT_MANAGER -> R.drawable.ic_projectmanager
                PRODUCT_MANAGER -> R.drawable.ic_product_manager
                FULL_STACK_DEVELOPER -> R.drawable.fullstack
                TALENT_ACQUISITION -> R.drawable.talent_acquisition
                BACKEND_DEVELOPER -> R.drawable.backend
                PPC_ANALYST -> R.drawable.ic_php
                DEVOPS -> R.drawable.devops
                HYBRID_APP_DEVELOPER -> R.drawable.fullstack
                QUALITY_ASSURANCE_ENGINEER -> R.drawable.quality
                SALES_PROFESSIONAL -> R.drawable.sales
                GRAPHIC_DESIGNER -> R.drawable.designer
                UI_DESIGNER -> R.drawable.ic_uidesigner
                else -> R.drawable.backend
            }
            ivCardLogo.setImageResource(skillImage)
        }
    }

    companion object {
        private const val ANDROID_DEVELOPER = "Android Developer"
        private const val IOS_DEVELOPER = "iOS Developer"
        private const val PROJECT_MANAGER = "Project Manager"
        private const val PRODUCT_MANAGER = "Product Manager"
        private const val FULL_STACK_DEVELOPER = "Full Stack Developer"
        private const val TALENT_ACQUISITION = "Talent Acquisition"
        private const val BACKEND_DEVELOPER = "Backend developer"
        private const val PPC_ANALYST = "PPC Analyst"
        private const val HYBRID_APP_DEVELOPER = "Hybrid App Developer"
        private const val DEVOPS = "DevOps"
        private const val QUALITY_ASSURANCE_ENGINEER = "Quality Assurance Engineer"
        private const val SALES_PROFESSIONAL = "Sales Professional"
        private const val GRAPHIC_DESIGNER = "Graphic Designer"
        private const val UI_DESIGNER = "UI/UX Designer"

    }
}