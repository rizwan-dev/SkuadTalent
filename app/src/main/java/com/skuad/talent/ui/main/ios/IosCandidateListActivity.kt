package com.skuad.talent.ui.main.ios

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import com.skuad.talent.R
import com.skuad.talent.databinding.ActivityAndroidCandidateListBinding
import com.skuad.talent.databinding.ActivityIosCandidateListBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.android.AndroidCandidateListActivity

class IosCandidateListActivity : BaseActivityVB<ActivityIosCandidateListBinding>() {

    override fun attachBinding(
        list: MutableList<ActivityIosCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityIosCandidateListBinding.inflate(layoutInflater))
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
            supportActionBar?.title = "iOS"

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
            Intent(context, IosCandidateListActivity::class.java)
    }
}