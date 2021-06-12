package com.skuad.talent.ui.main.candiatedetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.candidate.CandidateDetailsRequest
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.domain.entities.changestate.ChangeStateRequest
import com.skuad.talent.domain.entities.changestate.ChangeStateResponse
import com.skuad.talent.domain.repository.CandidateRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class CandidateDetailsViewModel @Inject constructor(private val candidateRepo: CandidateRepo) : ViewModel() {

    val candidateLiveData : LiveData<ResourceState<GetCandidateByAdmin>> get() = _candidateLiveData

    private var _candidateLiveData = MutableLiveData<ResourceState<GetCandidateByAdmin>>()

    var userId: String? = null

    var changeStage = ""

    var resumeUrl = ""


    val changeStateLiveData : LiveData<ResourceState<ChangeStateResponse>> get() = _changeStateLiveData
    private var _changeStateLiveData = MutableLiveData<ResourceState<ChangeStateResponse>>()

    fun getCandidateDetails(id: String, requirementId: String){
        viewModelScope.launch {
            val result = candidateRepo.getCandidateDetails(CandidateDetailsRequest(id, requirementId))
            _candidateLiveData.value = result
        }
    }

    fun changeCandidateState(id: String, stage: String){
        changeStage = stage
        viewModelScope.launch {
            val result = candidateRepo.changeState(ChangeStateRequest(id, stage))
            _changeStateLiveData.value = result
        }
    }

}