package com.skuad.talent.ui.main.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.login.GoogleRequest
import com.skuad.talent.domain.entities.login.LoginResponse
import com.skuad.talent.domain.entities.login.LoginResponseData
import com.skuad.talent.domain.entities.login.SocialLoginResponse
import com.skuad.talent.domain.repository.LoginRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginRepo: LoginRepo) : ViewModel() {

    val tokenLiveData: LiveData<ResourceState<SocialLoginResponse>> get() = _tokeLiveData

    private val _tokeLiveData = MutableLiveData<ResourceState<SocialLoginResponse>>()

    val loginResponseData: LiveData<ResourceState<LoginResponseData>> get() = _loginResponseData

    private val _loginResponseData = MutableLiveData<ResourceState<LoginResponseData>>()

    fun getFirebaseToken(idToken: String){
        viewModelScope.launch {
            val result = loginRepo.googleSignInAsync(GoogleRequest(idToken))
            _tokeLiveData.value = result
        }
    }

    fun loginFromServer(token: String){
        viewModelScope.launch {
            val loginResult = loginRepo.loginFirebaseAsync(token)
            _loginResponseData.value = loginResult
        }
    }

}