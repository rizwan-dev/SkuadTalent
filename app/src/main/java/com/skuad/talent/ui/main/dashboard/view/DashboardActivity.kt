package com.skuad.talent.ui.main.dashboard.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.ActivityDashboardBinding
import com.skuad.talent.domain.entities.dashboard.SkillsInfo
import com.skuad.talent.domain.repository.SharedPrefRepo
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity
import com.skuad.talent.ui.main.candidatelist.view.CandidateListActivity.Companion.REQUEST_CHANGE_STATE
import com.skuad.talent.ui.main.dashboard.adapter.DashboardAdapter
import com.skuad.talent.ui.main.dashboard.vewmodel.DashboardViewModel
import com.skuad.talent.ui.main.login.view.LoginActivity
import com.skuad.talent.utils.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.lougout_confirmation_dialog.*
import timber.log.Timber
import javax.inject.Inject


class DashboardActivity : BaseActivityVB<ActivityDashboardBinding>() {

    @Inject
    lateinit var viewModel: DashboardViewModel

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var sharedPrefRepo: SharedPrefRepo

    private val dashboardList = mutableListOf<SkillsInfo>()

    lateinit var dashboardAdapter: DashboardAdapter

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun attachBinding(
        list: MutableList<ActivityDashboardBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityDashboardBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setupObserver()
        getDashBoardData()
        setUpView()
        setUpLogout()
    }




    private fun setUpLogout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
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
                    dashboardList.clear()
                    dashboardList.addAll(cardList)
                    dashboardAdapter.notifyDataSetChanged()
                }
                is ResourceState.Failure -> {
                    Timber.e(it.exception)
                }
            }
        })

    }

    private fun setUpView() {
        withBinding {
            rvDashboard.layoutManager = GridLayoutManager(this@DashboardActivity, 2)
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dimen_20)
            rvDashboard.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, false))
            dashboardAdapter = DashboardAdapter(this@DashboardActivity, dashboardList) {
                startActivityForResult(
                    CandidateListActivity.newInstance(
                        this@DashboardActivity,
                        it.name, it.id
                    ), REQUEST_CHANGE_STATE
                )
            }
        }
        rvDashboard.adapter = dashboardAdapter
        iv_logout.setSafeOnClickListener {
            showCnfDialog()
        }

    }

    private fun showCnfDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.lougout_confirmation_dialog)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_10)
        dialog.setTitle(getString(R.string.logout_title))
        dialog.setCancelable(true)
        val textView: TextView = dialog.findViewById(R.id.tv_description)
        textView.text = getString(R.string.cnf_logout)

        val btnOk: TextView = dialog.findViewById(R.id.btn_ok_dialog)
        btnOk.setOnClickListener {
            logout()
            setResult(RESULT_OK)
            finish()
            dialog.dismiss()
        }
        val btnCancel: TextView = dialog.findViewById(R.id.btn_cancel_dialog)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        val width = (resources.displayMetrics.widthPixels * 0.75).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show()
    }

    private fun logout() {
        firebaseAuth.signOut()
        sharedPrefRepo.setAccessToken("")
        startActivity(LoginActivity.newInstance(this))
        finish()
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