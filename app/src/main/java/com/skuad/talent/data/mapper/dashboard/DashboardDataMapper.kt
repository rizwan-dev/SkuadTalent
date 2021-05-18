package com.skuad.talent.data.mapper.dashboard

import com.skuad.talent.GetDashboardRoleswithCountQuery
import com.skuad.talent.data.mapper.ResponseMapper

import com.skuad.talent.domain.entities.dashboard.SkillsInfo
import com.skuad.talent.domain.entities.dashboard.StageCount

class DashboardDataMapper : ResponseMapper<GetDashboardRoleswithCountQuery.Data, List<SkillsInfo>> {

    override fun map(input: GetDashboardRoleswithCountQuery.Data): List<SkillsInfo> =
        mapToDashboardData(input)

    private fun mapToDashboardData(input: GetDashboardRoleswithCountQuery.Data): List<SkillsInfo> {
        return input.dashboardRoleswithCount.map {
            SkillsInfo(
                id = it._id(),
                name = it.name(),
                stageCount = mapToStageCount(it.stageCount())
            )
        }
    }

    private fun mapToStageCount(stageCount: GetDashboardRoleswithCountQuery.StageCount): StageCount {
        return stageCount.run {
            StageCount(
                sourced = sourced(),
                hired = hired(),
                offered = offered(),
                registered = registered()
            )
        }
    }


}
