package com.skuad.talent.ui.base.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.core.view.minusAssign
import androidx.core.view.plusAssign
import androidx.fragment.app.Fragment
import com.skuad.talent.R

class PageLoadingProgress : LinearLayout {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_page_loading_progress, this, true)
        isClickable = true
        isFocusable = true
        isFocusableInTouchMode = true
    }

    fun showFromActivity(activity: AppCompatActivity) {
        try {
            val rootView = getViewGroup(activity)
            if (!rootView.contains(this)) rootView += this
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun hideFromActivity(activity: AppCompatActivity) {
        try {
            val rootView = getViewGroup(activity)
            if (rootView.contains(this)) rootView -= this
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun showFromFragment(fragment: Fragment) {
        try {
            val rootView = getViewGroup(fragment)
            if (!rootView.contains(this)) rootView.addView(this)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun hideFromFragment(fragment: Fragment) {
        try {
            val rootView = getViewGroup(fragment)
            if (rootView.contains(this)) rootView.minusAssign(this)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun isVisible(activity: AppCompatActivity) = getViewGroup(activity).contains(this)

    private fun getViewGroup(activity: AppCompatActivity): ViewGroup {
        return activity.window.decorView.findViewById(android.R.id.content) as ViewGroup
    }

    private fun getViewGroup(fragment: Fragment): ViewGroup {
        return fragment.view?.findViewById(R.id.content)
            ?: throw NullPointerException("add android:id=@+id/content to fragment top view group")
    }
}