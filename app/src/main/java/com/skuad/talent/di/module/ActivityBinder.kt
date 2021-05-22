package com.skuad.talent.di.module

import com.skuad.talent.ui.main.candiatedetails.view.CandidateDetailActivity
import com.skuad.talent.ui.main.candidatelist.view.NewCandidateListActivity
import com.skuad.talent.ui.main.dashboard.view.NewDashboardActivity
import com.skuad.talent.ui.main.login.LoginActivity
import com.skuad.talent.ui.main.login.SignUpActivity
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
    abstract fun bindSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector
    abstract fun bindNewDashboardActivity(): NewDashboardActivity

    @ContributesAndroidInjector
    abstract fun bindNewCandidateListActivity(): NewCandidateListActivity

    @ContributesAndroidInjector
    abstract fun bindCandidateDetailActivity(): CandidateDetailActivity
}