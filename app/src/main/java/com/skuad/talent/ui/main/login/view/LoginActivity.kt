package com.skuad.talent.ui.main.login.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.NewActivityLoginBinding
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.extension.showShortToast
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.dashboard.view.NewDashboardActivity
import com.skuad.talent.ui.main.login.SignUpActivity
import com.skuad.talent.ui.main.login.viewmodel.LoginViewModel
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : BaseActivityVB<NewActivityLoginBinding>() {

    @Inject
    lateinit var viewModel: LoginViewModel

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mGoogleSignInOptions: GoogleSignInOptions? = null

    override fun attachBinding(list: MutableList<NewActivityLoginBinding>, inflater: LayoutInflater) {
        list.add(NewActivityLoginBinding.inflate(layoutInflater))
    }

    override fun setup() {
        googleSignInSetup()
        setupClickListener()
        setupLiveDataObserver()
    }

    private fun setupLiveDataObserver() {
        viewModel.tokenLiveData.observe(this, Observer {
            showLoading(false)
            when(it){
                is ResourceState.Success -> {
                    Timber.d("Auth Token -> ${it.body.authToken}")
                    showLoading(true)
                    viewModel.getFirebaseToken(it.body.authToken)
                }

                is ResourceState.Failure -> {
                    Timber.e(it.exception)
                }
            }
        })
        viewModel.loginResponseData.observe(this, Observer {
            showLoading(false)
            when(it){
                is ResourceState.Success -> {
                    Timber.d("Login Response -> ${it.body.success}")
                }

                is ResourceState.Failure -> {
                    Timber.e(it.exception)
                }
            }
        })
    }

    private fun setupClickListener() {
        withBinding {
            btnLogin.setSafeOnClickListener {
                startActivity(NewDashboardActivity.newInstance(this@LoginActivity))
            }
            tvSignupInstruction.setSafeOnClickListener {
                startActivity(SignUpActivity.newInstance(this@LoginActivity))
            }

            btnSignInwithGoogle.setSafeOnClickListener {
                fetchGoogleAccessToken()
            }
        }
    }

    private fun fetchGoogleAccessToken() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun googleSignInSetup() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1090609532096-lf45nd2vlkk1pjbf1qfa7vjj5nek8u7t.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInOptions?.let {
            mGoogleSignInClient = GoogleSignIn.getClient(this, it)
        }
    }

    companion object{
        private const val RC_SIGN_IN = 1001
        fun newInstance(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Timber.d("Token received : ${account?.idToken}")
                account?.idToken?.let {
                    showLoading(true)
                    viewModel.getFirebaseToken(it)
                }
                mGoogleSignInClient?.signOut()
            } catch (e: ApiException) {
                Timber.e(e)
                showShortToast(getString(R.string.something_went_wrong))
            }
        }

    }

}