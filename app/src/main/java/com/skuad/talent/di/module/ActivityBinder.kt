package com.skuad.talent.di.module

import com.skuad.talent.ui.main.candiatedetails.CandidateDetailActivity
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity
import com.skuad.talent.ui.main.dashboard.view.NewDashboardActivity
import com.skuad.talent.ui.main.login.LoginActivity
import com.skuad.talent.ui.main.login.SignUpActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinder {

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector
    abstract fun bindNewDashboardActivity(): NewDashboardActivity

    @ContributesAndroidInjector
    abstract fun bindCandidateListActivity(): CandidateListActivity

    @ContributesAndroidInjector
    abstract fun bindCandidateDetailActivity(): CandidateDetailActivity
}