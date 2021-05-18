package com.skuad.talent.ui.main.dashboard.vewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.dashboard.DashboardCategoriesCount
import com.skuad.talent.domain.entities.dashboard.SkillsInfo
import com.skuad.talent.domain.repository.DashboardRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val dashboardRepo: DashboardRepo) : ViewModel() {

    val dashBoardListLiveData : LiveData<ResourceState<List<SkillsInfo>>> get() = _dashBoardListLiveData

    private var _dashBoardListLiveData = MutableLiveData<ResourceState<List<SkillsInfo>>>()

    val dashBoardCategoriesListLiveData : LiveData<ResourceState<DashboardCategoriesCount>> get() = _dashBoardCategoriesListLiveData

    private var _dashBoardCategoriesListLiveData = MutableLiveData<ResourceState<DashboardCategoriesCount>>()

    fun getDashboardData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = dashboardRepo.getDashboardData()
                _dashBoardListLiveData.postValue(result)
            }

        }
    }

    fun getDashboardCategoriesData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = dashboardRepo.getDashboardCategoriesCount()
                _dashBoardCategoriesListLiveData.postValue(result)
            }

        }
    }

}