package com.skuad.talent.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skuad.talent.di.ViewModelProviderFactory
import com.skuad.talent.di.scope.ViewModelKey
import com.skuad.talent.ui.main.candiatedetails.viewmodel.CandidateDetailsViewModel
import com.skuad.talent.ui.main.candidatelist.viewmodel.CandidateListViewModel
import com.skuad.talent.ui.main.dashboard.vewmodel.DashboardViewModel
import com.skuad.talent.ui.main.login.viewmodel.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelBinder {

    @Binds
    @Singleton
    abstract fun providesViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun providesDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CandidateDetailsViewModel::class)
    abstract fun providesCandidateDetailsViewModel(candidateDetailsViewModel: CandidateDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CandidateListViewModel::class)
    abstract fun providesCandidateListViewModel(candidateListViewModel: CandidateListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun providesLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}