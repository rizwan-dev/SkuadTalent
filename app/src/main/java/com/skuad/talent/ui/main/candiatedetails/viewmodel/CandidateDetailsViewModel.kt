package com.skuad.talent.ui.main.candiatedetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.candidate.CandidateDetailsRequest
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.domain.repository.CandidateRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class CandidateDetailsViewModel @Inject constructor(private val candidateRepo: CandidateRepo) : ViewModel() {

    val candidateLiveData : LiveData<ResourceState<GetCandidateByAdmin>> get() = _candidateLiveData

    private var _candidateLiveData = MutableLiveData<ResourceState<GetCandidateByAdmin>>()

    fun getCandidateDetails(id: String, requirementId: String){
        viewModelScope.launch {
            val result = candidateRepo.getCandidateDetails(CandidateDetailsRequest(id, requirementId))
            _candidateLiveData.value = result
        }
    }

}