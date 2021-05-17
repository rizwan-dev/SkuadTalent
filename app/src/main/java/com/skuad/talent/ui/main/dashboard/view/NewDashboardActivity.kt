package com.skuad.talent.ui.main.dashboard.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.NewDashboardActivityBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity
import com.skuad.talent.ui.main.candidatelist.view.NewCandidateListActivity
import com.skuad.talent.ui.main.dashboard.adapter.DashboardAdapter
import com.skuad.talent.ui.main.dashboard.vewmodel.DashboardViewModel
import com.skuad.talent.utils.DataUtil
import com.skuad.talent.utils.GridSpacingItemDecoration
import timber.log.Timber
import javax.inject.Inject


class NewDashboardActivity : BaseActivityVB<NewDashboardActivityBinding>() {

    @Inject
    lateinit var viewModel: DashboardViewModel

    override fun attachBinding(
        list: MutableList<NewDashboardActivityBinding>,
        inflater: LayoutInflater
    ) {
        list.add(NewDashboardActivityBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setUpView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getDashboardData()
        viewModel.getDashboardCategoriesData()
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

        viewModel.dashBoardCategoriesListLiveData.observe(this, Observer {
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

    private fun setUpView() {
        withBinding {
            val cardList = DataUtil.getCardsList()
            rvDashboard.layoutManager = GridLayoutManager(this@NewDashboardActivity, 2)
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dimen_20dp)
            rvDashboard.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, false))
            rvDashboard.adapter = DashboardAdapter(this@NewDashboardActivity, cardList) { card ->
                startActivity(
                    NewCandidateListActivity.newInstance(
                        this@NewDashboardActivity,
                        card.title
                    )
                )
            }
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, NewDashboardActivity::class.java)
    }
}