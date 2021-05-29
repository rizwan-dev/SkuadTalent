package com.skuad.talent.ui.main.candidatelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.candidatelist.CandidateInfo
import com.skuad.talent.domain.entities.candidatelist.CandidateListRequest
import com.skuad.talent.domain.repository.CandidateRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class CandidateListViewModel @Inject constructor(private val candidateRepo: CandidateRepo) : ViewModel() {

    val candidateListLiveData: LiveData<ResourceState<List<CandidateInfo>>> get() = _candidateListLiveData

    var isDataChanged = false

    private val _candidateListLiveData = MutableLiveData<ResourceState<List<CandidateInfo>>>()

    fun getCandidateInfo(candidateListRequest: CandidateListRequest){
        viewModelScope.launch {
            val result = candidateRepo.getCandidateList(candidateListRequest)
            _candidateListLiveData.value = result
        }
    }
}