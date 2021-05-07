package com.skuad.talent.ui.main.candidatelist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.skuad.talent.R
import com.skuad.talent.data.model.Candidate
import com.skuad.talent.databinding.ActivityCandidateListBinding
import com.skuad.talent.ui.base.BaseActivityVB

class CandidateListActivity : BaseActivityVB<ActivityCandidateListBinding>() {

    private lateinit var skillName: String

    override fun attachBinding(
        list: MutableList<ActivityCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityCandidateListBinding.inflate(layoutInflater))
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
            val title = when(skillName){

                ANDROID -> "ANDROID"

                IOS -> "iOS"

                BACKEND -> "BACKEND"

                else -> ""
            }
            supportActionBar?.title = title
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setUpView() {
        skillName = intent.getStringExtra(SKILL_NAME) ?: ""
        withBinding {
            val candidateList = getCandidateList()
            rvAndroid.layoutManager = LinearLayoutManager(this@CandidateListActivity)
            rvAndroid.adapter = CandidateListAdapter(candidateList=candidateList)
        }
    }


    private fun getCandidateList() = listOf(
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
        const val SKILL_NAME = "skill_name"
        const val ANDROID = "android"
        const val IOS = "ios"
        const val BACKEND = "backend"
        fun newInstance(context: Context, skillName: String) =
            Intent(context, CandidateListActivity::class.java).apply {
                putExtra(SKILL_NAME, skillName)
            }
    }
}