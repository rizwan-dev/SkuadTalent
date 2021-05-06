package com.skuad.talent.ui.main.backend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import com.skuad.talent.R
import com.skuad.talent.databinding.ActivityBackendCandidateListBinding
import com.skuad.talent.databinding.ActivityIosCandidateListBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.android.AndroidCandidateListActivity

class BackendCandidateListActivity : BaseActivityVB<ActivityBackendCandidateListBinding>() {

    override fun attachBinding(
        list: MutableList<ActivityBackendCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityBackendCandidateListBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setUpView()
        setToolBar()
    }

    private fun setUpView() {
        withBinding {

        }
    }

    private fun setToolBar() {
        withBinding {
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            supportActionBar?.title = "BACKEND"

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    companion object {
        fun newInstance(context: Context) =
            Intent(context, BackendCandidateListActivity::class.java)
    }
}