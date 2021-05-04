package com.skuad.talent.extension

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.google.android.material.snackbar.Snackbar

fun View.setVisibility(isVisible: Boolean): Unit =
        if (isVisible) visibility = View.VISIBLE else visibility = View.GONE

fun View.setVisibilityInvisible(isVisible: Boolean): Unit =
        if (isVisible) visibility = View.VISIBLE else visibility = View.INVISIBLE

fun View.setSafeOnClickListener(defaultInterval: Int = 1000, onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener(defaultInterval) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

class SafeClickListener(
        private var defaultInterval: Int,
        private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun Group.addOnClickListener(listener: (view: View) -> Unit) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

fun TextView.setTextOrHide(value: String?) {
    if (value.isNullOrBlank()) {
        this.setVisibility(false)
    } else {
        this.setVisibility(true)
        this.text = value
    }
}

fun TextView.setTextOrInvisible(value: String?) {
    if (value.isNullOrBlank()) {
        this.setVisibilityInvisible(false)
    } else {
        this.setVisibilityInvisible(true)
        this.text = value
    }
}


private fun isValidContextForGlide(context: Context?): Boolean {
    if (context is Activity)
        return !context.isDestroyed && !context.isFinishing

    return true
}

fun View.showShortSnack(
        message: String = "",
        length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, message, length).show()
}

fun View.showLongSnack(
        message: String = "",
        length: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(this, message, length).show()
}

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}


