package net.zenpro.base.abstracts.vb

import androidx.viewbinding.ViewBinding

/**
 * This interface defines methods which can be only used to get or check binding instance
 * and NOT setting the binding instance
 */
interface VBHelpers<VBI : ViewBinding> {
    fun isBindingAvailable(): Boolean

    fun getBinding(): VBI

    //just to be used in areas where the caller is unsure of weather the binding instance is alive or not
    fun getNullableBinding(): VBI?

    fun usingBinding(block: ((binding: VBI) -> Unit))

    fun withBinding(block: (VBI.() -> Unit)): VBI
}