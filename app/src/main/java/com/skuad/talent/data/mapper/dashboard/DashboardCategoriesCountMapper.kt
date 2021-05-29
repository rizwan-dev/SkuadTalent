package com.skuad.talent.data.mapper.dashboard

import com.skuad.talent.GetDashboardCategoriesCountQuery
import com.skuad.talent.data.mapper.ResponseMapper
import com.skuad.talent.domain.entities.dashboard.DashboardCategoriesCount

class DashboardCategoriesCountMapper : ResponseMapper<GetDashboardCategoriesCountQuery.Data, DashboardCategoriesCount> {
    override fun map(input: GetDashboardCategoriesCountQuery.Data): DashboardCategoriesCount {
        return input.dashboardCategoriesCount.run {
            DashboardCategoriesCount(sourced = sourced(), registered = hired())
        }
    }
}