package com.skuad.talent.ui.base

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.skuad.talent.ui.base.vb.VBHelpersImpl
import com.skuad.talent.ui.base.vb.VBHelpersImpl.Companion.ERROR_ADD_BINDING_TO_LIST
import net.zenpro.base.abstracts.vb.VBHelpers


abstract class BaseActivityVB<VB_CHILD : ViewBinding>(
    private val helperImpl: VBHelpersImpl<VB_CHILD> = VBHelpersImpl()
) : BaseActivity(), VBHelpers<VB_CHILD> by helperImpl {
    override val contentView = VBHelpersImpl.RES_NO_LAYOUT


    override fun getExternallyInflatedView(inflater: LayoutInflater): View {
        val bindingList = mutableListOf<VB_CHILD>()
        attachBinding(bindingList, inflater)
        val binding: VB_CHILD = bindingList.getOrNull(0) ?: error(ERROR_ADD_BINDING_TO_LIST)
        helperImpl.updateBinding(binding)
        return helperImpl.getBinding().root
    }


    abstract fun attachBinding(
        list: MutableList<VB_CHILD>,
        inflater: LayoutInflater
    )

    override fun onDestroy() {
        super.onDestroy()
        helperImpl.destroyBinding()
    }
}