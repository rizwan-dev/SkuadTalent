package com.skuad.talent.ui.main.dashboard.vewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.SkillsInfo
import com.skuad.talent.domain.repository.DashboardRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val dashboardRepo: DashboardRepo) : ViewModel() {

    val dashBoardListLiveData : LiveData<ResourceState<List<SkillsInfo>>> get() = _dashBoardListLiveData

    private var _dashBoardListLiveData = MutableLiveData<ResourceState<List<SkillsInfo>>>()

    fun getDashboardData(){
        viewModelScope.launch {
            val result = dashboardRepo.getDashBoardData()
            _dashBoardListLiveData.value = result
        }
    }

}