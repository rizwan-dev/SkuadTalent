package com.skuad.talent.ui.main.candiatedetails.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.ActivityCandidateProfileBinding
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.extension.setVisibility
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candiatedetails.viewmodel.CandidateDetailsViewModel
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil
import kotlinx.android.synthetic.main.alert_dialog.*
import timber.log.Timber
import javax.inject.Inject


class CandidateDetailActivity : BaseActivityVB<ActivityCandidateProfileBinding>(),
    DownloadFile.Listener {

    @Inject
    lateinit var viewModel: CandidateDetailsViewModel

    var adapter: PDFPagerAdapter? = null

    var remotePDFViewPager: RemotePDFViewPager? = null
    override fun attachBinding(
        list: MutableList<ActivityCandidateProfileBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityCandidateProfileBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setToolBar()
        setupObserver()
        addListeners()
    }

    private fun addListeners() {
        withBinding {
            btnSelected.setSafeOnClickListener {
                Timber.d("+++++On click of Selected btn")
                changeState(SELECTED)
            }

            btnRejected.setSafeOnClickListener {
                changeState(REJECTED)
            }
        }
    }


    private fun showAlertBox(stage: String) {

        Timber.e("+++++IN ALERT BOX")
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.alert_dialog)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_10)
        dialog.setTitle("Profile Status")
        dialog.setCancelable(true)
        val textView: TextView = dialog.findViewById(R.id.tv_description)
        if (stage.equals("registered")) {
            textView.text = "This profile has been shortlisted."
        } else {
            textView.text = "This profile has been rejected."
        }

        val button: TextView = dialog.findViewById(R.id.btn_ok_dialog)
        button.setOnClickListener {
            Timber.d("+++++On click of OK btn in alert")
            setResult(RESULT_OK)
            finish()
            dialog.dismiss()
        }
        val width = (resources.displayMetrics.widthPixels * 0.75).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show()
    }


    private fun changeState(stage: String) {
        Timber.d("+++++In change state fun--->$stage")
        viewModel.userId?.let { uId ->
            showLoading(true)
            viewModel.changeCandidateState(uId, stage)
            showAlertBox(stage)
        }

    }

    private fun setupObserver() {
        val candidateId = intent.getStringExtra(CANDIDATE_ID)
        Timber.e("uid of Selected candidate ----> %s", candidateId)
        showLoading(true)
        candidateId?.let { viewModel.getCandidateDetails(it, "") }
        viewModel.candidateLiveData.observe(this, {
            showLoading(false)
            when (it) {
                is ResourceState.Success -> {
                    Timber.d("Candidate details is ${it.body}")
                    val candidateData = it.body
                    Timber.d("Candidate details is-----> ${candidateData}")
                    viewModel.userId = candidateData.uid
                    setUpView(candidateData)
                }

                is ResourceState.Failure -> {
                    Timber.e(it.exception)
                }
            }
        })

        viewModel.changeStateLiveData.observe(this, Observer {
            showLoading(false)
            Timber.d("In observer ")
            when (it) {
                is ResourceState.Success -> {
                    Timber.d("+++++State change success " + it.body)


                }

                is ResourceState.Failure -> {
                    Timber.d("State change failed ")
                    Timber.e(it.exception)
                }
            }
        })
    }

    private fun setToolBar() {
        withBinding {
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            supportActionBar?.title = "Profile Details"
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setUpView(candidateData: GetCandidateByAdmin) {
        val resumeUrl =
            "https://firebasestorage.googleapis.com/v0/b/fir-test-f324a.appspot.com/o/Pavan%20Bilagi_Halodoc.pdf?alt=media&token=178c0ed0-e012-4e6d-a237-d86c8c72d670"

        withBinding {

            if (resumeUrl == null) {
                tvNoResume.setVisibility(true)
            } else {
                tvNoResume.setVisibility(false)
                showLoading(true)
                remotePDFViewPager = RemotePDFViewPager(
                    this@CandidateDetailActivity,
                    resumeUrl,
                    this@CandidateDetailActivity
                )
                updateLayout()

            }
            //name
            tvCandidateName.text = candidateData.contact_info?.name
            //address
            if (!candidateData.contact_info?.address.isNullOrEmpty()) {
                tvAddress.text = candidateData.contact_info?.address
            } else {
                tvAddress.text = "Address : NA"
            }
            if (!candidateData.experience.isNullOrEmpty()) {

                if (!candidateData.role_id?.name.isNullOrEmpty()) {
                    val designation = candidateData.role_id?.name
                    tvDesignation.text =
                        "$designation | " + (candidateData.experience[0].experience
                            ?: "").plus(" years")
                } else {
                    val designation = "Designation : NA"
                    tvDesignation.text =
                        "$designation | " + (candidateData.experience[0].experience
                            ?: "").plus(" years")
                }

            } else {
                tvDesignation.text = getString(R.string.designation_exp_not_available)
            }

            if (!candidateData.experience.isNullOrEmpty() && !candidateData.experience[0].company_id.isNullOrEmpty()

            ) {

                tvCurrentEmployer.text = candidateData.experience[0].company_id

            } else {
                tvCurrentEmployer.text = getString(R.string.current_employer_not_available)
            }
            //
            if (!candidateData.experience.isNullOrEmpty()
                && !candidateData.experience[0].salary?.currency.isNullOrEmpty()
                && !candidateData.experience[0].salary?.amount?.toString().isNullOrEmpty()
            ) {

                val currency = candidateData.experience[0].salary?.currency
                val amount = candidateData.experience[0].salary?.amount?.toInt()

                tvSalary.text = currency + " " + amount
            } else {
                tvSalary.text = getString(R.string.salary_not_available)
            }
            //
            if (!candidateData.preferences?.notice_period?.toString().isNullOrEmpty()) {
                tvNoticePeriod.text = candidateData.preferences?.notice_period?.toString()
            } else {
                tvNoticePeriod.text = getString(R.string.notice_period_not_available)
            }


        }
    }

    private fun updateLayout() {
        getBinding().webView.addView(
            remotePDFViewPager,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    companion object {
        const val CANDIDATE_ID = "candidate_id"

        private const val SELECTED = "registered"

        private const val REJECTED = "disqualified"

        fun newInstance(context: Context, uid: String?) =
            Intent(context, CandidateDetailActivity::class.java).apply {
                putExtra(CANDIDATE_ID, uid)
            }
    }

    override fun onSuccess(url: String?, destinationPath: String?) {
        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
        remotePDFViewPager?.adapter = adapter
        showLoading(false)
    }

    override fun onFailure(e: Exception?) {
        showLoading(false)
    }

    override fun onProgressUpdate(progress: Int, total: Int) {

    }

    override fun onDestroy() {
        adapter?.let { it.close() }
        super.onDestroy()
    }
}
