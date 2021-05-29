package com.skuad.talent.ui.main.dashboard.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.NewDashboardActivityBinding
import com.skuad.talent.domain.entities.dashboard.SkillsInfo
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity.Companion.REQUEST_CHANGE_STATE
import com.skuad.talent.ui.main.dashboard.adapter.DashboardAdapter
import com.skuad.talent.ui.main.dashboard.vewmodel.DashboardViewModel
import com.skuad.talent.utils.GridSpacingItemDecoration
import timber.log.Timber
import javax.inject.Inject


class DashboardActivity : BaseActivityVB<NewDashboardActivityBinding>() {

    @Inject
    lateinit var viewModel: DashboardViewModel

    override fun attachBinding(
        list: MutableList<NewDashboardActivityBinding>,
        inflater: LayoutInflater
    ) {
        list.add(NewDashboardActivityBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setupObserver()
        getDashBoardData()
    }

    private fun getDashBoardData() {
        showLoading(true)
        viewModel.getDashboardData()
    }

    private fun setupObserver() {
        viewModel.dashBoardListLiveData.observe(this, Observer {
            showLoading(false)
            when (it) {
                is ResourceState.Success -> {
                    Timber.d("Dashboard data is ${it.body}")
                    val cardList = it.body
                    setUpView(cardList)

                }
                is ResourceState.Failure -> {
                    Timber.e(it.exception)
                }
            }
        })

    }

    private fun setUpView(cardList: List<SkillsInfo>) {
        withBinding {
            rvDashboard.layoutManager = GridLayoutManager(this@DashboardActivity, 2)
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dimen_20)
            rvDashboard.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, false))
            rvDashboard.adapter = DashboardAdapter(this@DashboardActivity, cardList) {
                startActivityForResult(
                    CandidateListActivity.newInstance(
                        this@DashboardActivity,
                        it.name, it.id
                    ), REQUEST_CHANGE_STATE
                )
            }
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, DashboardActivity::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CHANGE_STATE) {
            getDashBoardData()
        }
    }
}