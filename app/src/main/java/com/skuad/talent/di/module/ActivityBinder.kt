package com.skuad.talent.di.module

import com.skuad.talent.ui.main.candiatedetails.view.CandidateDetailActivity
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity
import com.skuad.talent.ui.main.dashboard.view.DashboardActivity
import com.skuad.talent.ui.main.login.view.LoginActivity
import com.skuad.talent.ui.main.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinder {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindNewDashboardActivity(): DashboardActivity

    @ContributesAndroidInjector
    abstract fun bindNewCandidateListActivity(): CandidateListActivity

    @ContributesAndroidInjector
    abstract fun bindCandidateDetailActivity(): CandidateDetailActivity
}