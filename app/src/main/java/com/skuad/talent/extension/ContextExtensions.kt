package com.skuad.talent.extension

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.skuad.talent.R


fun Context.showLongToast(str: String) = Toast.makeText(this, str, Toast.LENGTH_LONG).show()

fun Context.showShortToast(str: String) = Toast.makeText(this, str, Toast.LENGTH_SHORT).show()

fun Context.getColorFromRes(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getDrawableFromRes(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)


fun Context.createAlertDialog(
        title: String? = null,
        message: String? = null,
        isCancelable: Boolean = true,
        positiveButtonText: String = "",
        negativeButtonText: String = "",
        positiveButtonAction: () -> Unit = {},
        negativeButtonAction: () -> Unit = {},
        onDialogCancelled: () -> Unit = {},
        dialogClickListener: DialogInterface.OnClickListener? = null,
        @StyleRes themeResId: Int? = null
): AlertDialog {
    var clickListener = DialogInterface.OnClickListener { dialog, which ->
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                positiveButtonAction()
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                negativeButtonAction()
                dialog.dismiss()
            }
        }
    }


    return if (themeResId != null) {
        AlertDialog.Builder(this, themeResId)
    } else {
        AlertDialog.Builder(this, R.style.AlertDialogTheme)
    }.apply {
        setTitle(title)
        setMessage(message)
        setCancelable(isCancelable)
        if (dialogClickListener != null) {
            clickListener = dialogClickListener
        }
        if (positiveButtonText.isNotBlank()) {
            setPositiveButton(positiveButtonText, clickListener)
        }
        if (negativeButtonText.isNotBlank()) {
            setNegativeButton(negativeButtonText, clickListener)
        }
        setOnCancelListener {
            onDialogCancelled()
        }
    }.create()
}
