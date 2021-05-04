package com.skuad.talent.ui.base.widget
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.annotation.RequiresApi

class AppCustomProgressBar : ProgressBar {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

}