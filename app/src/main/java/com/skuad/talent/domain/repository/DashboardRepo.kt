package com.skuad.talent.domain.repository

import com.skuad.talent.base.BaseRepo
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.DashboardCategoriesCount
import com.skuad.talent.domain.entities.SkillsInfo

interface DashboardRepo : BaseRepo{

    suspend fun getDashboardData(): ResourceState<List<SkillsInfo>>

    suspend fun getDashboardCategoriesCount(): ResourceState<DashboardCategoriesCount>
}