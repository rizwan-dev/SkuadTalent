package com.skuad.talent.ui.main.candidatelist.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.ActivityCandidateListBinding
import com.skuad.talent.domain.entities.candidatelist.CandidateInfo
import com.skuad.talent.domain.entities.candidatelist.CandidateListRequest
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candiatedetails.view.CandidateDetailActivity
import com.skuad.talent.ui.main.candidatelist.adapter.CandidateListAdapter
import com.skuad.talent.ui.main.candidatelist.viewmodel.CandidateListViewModel
import timber.log.Timber
import javax.inject.Inject

class NewCandidateListActivity : BaseActivityVB<ActivityCandidateListBinding>() {

    @Inject
    lateinit var viewModel: CandidateListViewModel

    private lateinit var cardTitle: String

    override fun attachBinding(
        list: MutableList<ActivityCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityCandidateListBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setToolBar()
//        setUpView()
        setupObserver()
    }

    private fun setupObserver() {

        getCandidateList()

        viewModel.candidateListLiveData.observe(this, Observer {
            showLoading(false)
            when (it) {
                is ResourceState.Success -> {
                    val candidateList = it.body
                    Timber.d("Candidate list is $candidateList")
                    setUpView(candidateList)

                }

                is ResourceState.Failure -> {
                    Timber.e(it.exception)
                }
            }
        })
    }

    private fun getCandidateList() {
        showLoading(true)
        viewModel.getCandidateInfo(CandidateListRequest(roleId = "5f9acb20130c9adc79c2e48d"))
    }

    private fun setToolBar() {

        withBinding {
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            cardTitle = intent.getStringExtra(CARD_TITLE) ?: ""
            Timber.e("cardTitle------------%s", cardTitle)
            supportActionBar?.title = cardTitle

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setUpView(candidateList: List<CandidateInfo>) {

        withBinding {
           // val candidateList = DataUtil.getAndroidCandidates()
            Timber.e("candidate list from DataUtil---->> %s", candidateList)
            rvAndroid.layoutManager = LinearLayoutManager(this@NewCandidateListActivity)
            rvAndroid.adapter =
                CandidateListAdapter(this@NewCandidateListActivity, candidateList) { candidate ->
                    startActivityForResult(
                        CandidateDetailActivity.newInstance(
                            this@NewCandidateListActivity,
                            candidate.uid
                        ), REQUEST_CHANGE_STATE
                    )
                }
        }
    }


    companion object {
        //to pass data from dashbord to list
        const val CARD_TITLE = "card_title"

        private const val REQUEST_CHANGE_STATE = 10001


        fun newInstance(context: Context, name: String) =
            Intent(context, NewCandidateListActivity::class.java).apply {
                putExtra(CARD_TITLE, name)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == REQUEST_CHANGE_STATE){
            getCandidateList()
        }
    }

}
