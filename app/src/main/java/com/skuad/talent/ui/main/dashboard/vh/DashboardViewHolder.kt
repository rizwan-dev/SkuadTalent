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
            tvActiveProfiles.text = skills.stageCount.sourced.toString()+" Profiles"


            val skillImage = when (skills.name) {
                ANDROID_DEVELOPER -> R.drawable.ic_androiddeveloper
                FULL_STACK_DEVELOPER -> R.drawable.ic_fullstackdeveloper
                IOS_DEVELOPER -> R.drawable.ic_ios
                DATA_ANALYST -> R.drawable.ic_php
                TECHNICAL_MANAGER -> R.drawable.ic_technicalmanager
                DEVOPS -> R.drawable.ic_devops__gcp_
                DB_ADMINISTRATOR -> R.drawable.ic_product_manager
                PPC_ANALYST -> R.drawable.ic_ppc_analyst
                ANDROID_DEVELOPER -> R.drawable.ic_androiddeveloper
                SALES_PROFESSIONAL -> R.drawable.sales
                QUALITY_ASSURANCE_ENGINEER -> R.drawable.ic_qa_automation_
                GRAPHIC_DESIGNER -> R.drawable.designer
                TALENT_ACQUISITION -> R.drawable.ic_talentacquisition
                FULL_STACK_DEVELOPER -> R.drawable.ic_fullstackdeveloper
                TWOD_ANIMATOR -> R.drawable.ic_php
                DATA_ENGINEER -> R.drawable.ic_dataengineer
                DATA_SCIENTIST -> R.drawable.ic_php
                UI_DESIGNER -> R.drawable.ic_uxdesigner
                FRONTEND_DEVELOPER-> R.drawable.ic_frontenddeveloper
                ACCOUNT_PAYABLE -> R.drawable.ic_php
                HYBRID_APP_DEVELOPER -> R.drawable.ic_php
                FRESHER-> R.drawable.ic_freshers
                BACKEND_DEVELOPER->R.drawable.ic_backenddeveloper
                PRODUCT_MANAGER -> R.drawable.ic_product_manager
                VFX_ARTIST -> R.drawable.ic_php
                CONTENT_WRITER->R.drawable.ic_php
                STORYBOARD_ARTIST-> R.drawable.ic_php
                ASSOCIATEDIRECTOR_EDITOR -> R.drawable.ic_php
                else -> R.drawable.ic_hr
            }
            ivCardLogo.setImageResource(skillImage)
        }
    }

    companion object {
        private const val DATA_ANALYST="Data Analyst"
        private const val DB_ADMINISTRATOR="DB Administrator"
        private const val TWOD_ANIMATOR="2D Animator"
        private const val DATA_ENGINEER = "Data Engineer"
        private const val DATA_SCIENTIST ="Data Scientist"
        private const val FRONTEND_DEVELOPER ="Frontend Developer"
        private const val ACCOUNT_PAYABLE="Account Payable"
        private const val FRESHER="Fresher"
        private const val VFX_ARTIST= "VFX Artist / SFX Artist"
        private const val CONTENT_WRITER="Content Writer / Scriptwriter"
        private const val STORYBOARD_ARTIST="Digital / Storyboard Artist"
        private const val ASSOCIATEDIRECTOR_EDITOR="Associate Director/Editor"
        private const val ANDROID_DEVELOPER = "Android Developer"
        private const val IOS_DEVELOPER = "iOS Developer"
        private const val TECHNICAL_MANAGER = "Technical Manager"
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