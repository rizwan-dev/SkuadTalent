package com.skuad.talent.data.mapper.dashboard

import com.skuad.talent.GetDashBoardDataQuery
import com.skuad.talent.data.mapper.ResponseMapper

import com.skuad.talent.domain.entities.SkillsInfo
import com.skuad.talent.domain.entities.StageCount

class DashboardDataMapper: ResponseMapper<GetDashBoardDataQuery.Data, List<SkillsInfo>> {

    override fun map(input: GetDashBoardDataQuery.Data): List<SkillsInfo> = mapToDashboardData(input)

    private fun mapToDashboardData(input: GetDashBoardDataQuery.Data): List<SkillsInfo> {
        return input.dashboardRoleswithCount.map { SkillsInfo(id = it._id(), name = it.name(), stageCount = mapToStageCount(it.stageCount())) }
    }

    private fun mapToStageCount(stageCount: GetDashBoardDataQuery.StageCount): StageCount {
        return stageCount.run { StageCount(sourced = sourced(), hired = hired(), offered = offered(), registered= registered() ) }
    }


}
