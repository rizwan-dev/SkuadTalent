package com.skuad.talent.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.skuad.talent.GetDashboardCategoriesCountQuery
import com.skuad.talent.GetDashboardRoleswithCountQuery
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.base.extensions.mapToEntity
import com.skuad.talent.data.mapper.dashboard.DashboardCategoriesCountMapper
import com.skuad.talent.data.mapper.dashboard.DashboardDataMapper
import com.skuad.talent.domain.entities.DashboardCategoriesCount
import com.skuad.talent.domain.entities.SkillsInfo
import com.skuad.talent.domain.repository.DashboardRepo

class DashboardRepoImpl(private val apolloClient: ApolloClient)  : DashboardRepo {

    override suspend fun getDashboardData(): ResourceState<List<SkillsInfo>> {
        return apolloClient.query(GetDashboardRoleswithCountQuery.builder().build()).mapToEntity { DashboardDataMapper().map(it) }
    }

    override suspend fun getDashboardCategoriesCount(): ResourceState<DashboardCategoriesCount> {
        return apolloClient.query(GetDashboardCategoriesCountQuery.builder().build()).mapToEntity { DashboardCategoriesCountMapper().map(it) }
    }
}