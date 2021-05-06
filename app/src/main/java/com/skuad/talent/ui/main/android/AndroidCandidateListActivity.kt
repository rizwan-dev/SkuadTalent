package com.skuad.talent.ui.main.android

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.skuad.talent.R
import com.skuad.talent.data.model.Candidate
import com.skuad.talent.databinding.ActivityAndroidCandidateListBinding
import com.skuad.talent.databinding.DashboardBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.login.LoginActivity

class AndroidCandidateListActivity : BaseActivityVB<ActivityAndroidCandidateListBinding>() {

    override fun attachBinding(
        list: MutableList<ActivityAndroidCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityAndroidCandidateListBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setUpView()
        setToolBar()
    }

    private fun setToolBar() {
        withBinding {
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            supportActionBar?.title = "ANDROID"

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setUpView() {
        withBinding {
            val candidateList = getCandidateList()
            rvAndroid.layoutManager = LinearLayoutManager(this@AndroidCandidateListActivity)
            rvAndroid.adapter = AndroidCandidateListAdapter(candidateList=candidateList)
        }
    }


    fun getCandidateList() = listOf<Candidate>(
        Candidate(
            candidateName = "Shailee Sharma",
            experience = "3 years",
            skills = "Android,Kotlin,Java basics,Manual Testing"
        ),
        Candidate(
            candidateName = "Shlok Parashar",
            experience = "2 years",
            skills = "C, Java basics"
        ),
        Candidate(
            candidateName = "Shubhi Jyoti Parashar",
            experience = "7.4 years",
            skills = "C, Java basics"
        ),
        Candidate(
            candidateName = "Shivi Ankit",
            experience = "7 years",
            skills = "Android,Kotlin,Flutter,Java"
        ),
        Candidate(
            candidateName = "Shrihan Sharma",
            experience = "6.5 years",
            skills = "Android,Java,Kotlin"
        ),
        Candidate(
            candidateName = "Shrinika Gargav",
            experience = "5 years",
            skills = "C++,Java basics,Android"
        )
    )

    companion object {
        fun newInstance(context: Context) =
            Intent(context, AndroidCandidateListActivity::class.java)
    }
}