package com.skuad.talent.ui.base.vb

import androidx.viewbinding.ViewBinding
import net.zenpro.base.abstracts.vb.VBHelpers


class VBHelpersImpl<T : ViewBinding> : VBHelpers<T> {

    private var binding: T? = null

    /** implemented functions coming from [VBHelpers] interface. These functions are accessible
     * both via [VBHelpersImpl] instance as well as classes implementing [VBHelpersImpl] by
     * delegation
     *
     */
    override fun getNullableBinding(): T? = binding

    override fun getBinding(): T {
        return binding ?: error(ERROR_BINDING_IS_NULL)
    }

    override fun usingBinding(block: (binding: T) -> Unit) {
        if (binding == null) {
            error(ERROR_BINDING_IS_NULL)
        } else {
            binding?.let { block.invoke(it) }
        }
    }

    override fun withBinding(block: T.() -> Unit): T {
        if (binding == null) {
            error(ERROR_BINDING_IS_NULL)
        } else {
            val bindingAfterRunning: T? = binding?.apply { block.invoke(this) }
            return bindingAfterRunning ?: error(ERROR_BINDING_IS_NULL)
        }
    }

    override fun isBindingAvailable(): Boolean {
        return binding != null
    }

    /** More functions : only accessible via [VBHelpersImpl] instance*/
    fun updateBinding(bindInstance: T) {
        this.binding = bindInstance
    }

    fun destroyBinding() {
        this.binding = null
    }

    companion object {
        const val RES_NO_LAYOUT = 0
        const val ERROR_NO_INFLATED_VIEW = "Either provide a layout res or binding view to the base class"
        const val ERROR_ADD_BINDING_TO_LIST = "Please add an instance of your xml's binding class to given list "
        const val ERROR_BINDING_IS_NULL = "Binding Not Found"
    }
}