package com.skuad.talent.domain.entities

data class DashboardCategoriesCount(
    val archive: Int = 0,
    val coding_test: Int = 0,
    val disqualified: Int = 0,
    val est: Int = 0,
    val hired: Int = 0,
    val mao: Int = 0,
    val mcq_test: Int = 0,
    val network: Int = 0,
    val offered: Int = 0,
    val passive: Int = 0,
    val registered: Int = 0,
    val sourced: Int = 0
)