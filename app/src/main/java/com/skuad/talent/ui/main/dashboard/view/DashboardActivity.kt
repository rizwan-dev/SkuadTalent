package com.skuad.talent.ui.main.dashboard.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.DashboardBinding
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity.Companion.ANDROID
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity.Companion.BACKEND
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity.Companion.IOS
import com.skuad.talent.ui.main.dashboard.vewmodel.DashboardViewModel
import timber.log.Timber
import javax.inject.Inject

class DashboardActivity : BaseActivityVB<DashboardBinding>() {

    @Inject
    lateinit var viewModel: DashboardViewModel

    override fun attachBinding(list: MutableList<DashboardBinding>, inflater: LayoutInflater) {
        list.add(DashboardBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setUpClickListener()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getDashboardData()
        viewModel.dashBoardListLiveData.observe(this, Observer {
            when(it){
                is ResourceState.Success -> {
                    Timber.d("Data is ${it.body}")
                }
                is ResourceState.Failure -> {
                    Timber.e(it.exception)
                }
            }
        })
    }

    private fun setUpClickListener() {
        withBinding {
            //for Android
            cvAndroid.setSafeOnClickListener {
                startActivity(CandidateListActivity.newInstance(this@DashboardActivity, ANDROID))
            }
            // for iOS
            cvIPhone.setSafeOnClickListener {
                startActivity(CandidateListActivity.newInstance(this@DashboardActivity, IOS))
            }
            // for Backend
            cvBackend.setSafeOnClickListener {
                startActivity(CandidateListActivity.newInstance(this@DashboardActivity, BACKEND))
            }
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, DashboardActivity::class.java)
    }

}