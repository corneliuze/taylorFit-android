package com.tailorfit.android.tailorfitapp.gig

import android.text.InputType
import com.tailorfit.android.R
import com.tailorfit.android.databinding.FragmentBaseFormBinding
import com.tailorfit.android.tailorfitapp.baseforms.BaseGigFormFragment
import com.tailorfit.android.tailorfitapp.baseforms.GigFormType
import com.tailorfit.android.tailorfitapp.models.request.CreateGigRequest

class AddGigPriceFragment : BaseGigFormFragment() {

    override fun getGigFormType(): GigFormType = GigFormType.AddGigPriceFragment

    override fun setDataHints(binding: FragmentBaseFormBinding) {
       binding.formDescription.text = getString(R.string.gig_price_message)
        binding.editText.hint = getString(R.string.gig_price_hint)
        binding.editText.inputType = InputType.TYPE_CLASS_NUMBER
    }

}