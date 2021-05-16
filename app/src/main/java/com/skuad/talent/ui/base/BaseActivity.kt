package com.skuad.talent.ui.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.skuad.talent.ui.base.vb.VBHelpersImpl
import com.skuad.talent.ui.base.widget.PageLoadingProgress
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import java.util.*

abstract class BaseActivity : DaggerAppCompatActivity() {

    abstract val contentView: Int
        @LayoutRes
        get

    protected var viewBindingEnabled = false

    val tag: String by lazy { this::class.java.simpleName }

    private val progressDialog by lazy { PageLoadingProgress(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
        } else{
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        attachLayout(layoutInflater)
        setup()
    }

    abstract fun setup()

    override fun onDestroy() {
        showLoading(false)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (!progressDialog.isVisible(this)) {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    fun showLoading(show: Boolean) {
        if (show) {
            with(progressDialog) {
                showFromActivity(this@BaseActivity)
                requestFocus()
            }
        } else {
            progressDialog.hideFromActivity(this)
        }
    }

    fun showBackButton(show: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
    }

    fun setToolBarTitle(title: String?) {
        supportActionBar?.title = title
    }


    private fun attachLayout(inflater: LayoutInflater) {
        val bindingView: View? = getExternallyInflatedView(inflater)
        when {
            bindingView != null -> setContentView(bindingView)
            contentView != VBHelpersImpl.RES_NO_LAYOUT -> setContentView(contentView)
            else -> error(VBHelpersImpl.ERROR_NO_INFLATED_VIEW)
        }
    }


    open fun getExternallyInflatedView(inflater: LayoutInflater): View? = null
}