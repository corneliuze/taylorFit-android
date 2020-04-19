package com.tailorfit.android.tailorfitapp.baseforms

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.tailorfit.android.base.BaseFragment
import com.tailorfit.android.base.BaseViewModel
import com.tailorfit.android.base.BaseViewModelFragment
import com.tailorfit.android.databinding.FragmentBaseFormBinding
import com.tailorfit.android.extensions.stringContent
import com.tailorfit.android.tailorfitapp.PrefsValueHelper
import com.tailorfit.android.tailorfitapp.gig.*
import com.tailorfit.android.tailorfitapp.models.request.CreateGigRequest
import com.tailorfit.android.tailorfitapp.validateTextLayouts
import com.tailorfit.android.utils.DatePickerFragment
import com.tailorfit.android.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_base_form.*
import javax.inject.Inject

enum class GigFormType {
    AddGigDueDateFragment,
    AddGigStyleFragment,
    AddGigTitleFragment,
    AddGigPriceFragment
}


abstract class BaseGigFormFragment : BaseFragment() {

    private lateinit var binding: FragmentBaseFormBinding

    @Inject
    lateinit var prefsValueHelper: PrefsValueHelper
    private var dateString = ""
    private var dob = ""
    private val REQUEST_CODE = 11


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseFormBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataHints(binding)
        navigate()
    }

    private fun navigate() {
        var title = ""
        var styleName = ""
        var date = ""
        var price = ""
        var style = listOf("", "", "")
        when (getGigFormType()) {
            GigFormType.AddGigTitleFragment -> {
                binding.FormProceedButton.setOnClickListener {
                    if (!validateTextLayouts(binding.editText)) {
                        prefsValueHelper.setGigTitle(binding.editText.stringContent())
                    }
                    findNavController().navigate(AddGigTitleFragmentDirections.actionAddGigTitleFragmentToAddGigStyleFragment())
                }
            }
            GigFormType.AddGigStyleFragment -> {
                binding.FormProceedButton.setOnClickListener {
                    if (!validateTextLayouts(binding.editText)) {
                        prefsValueHelper.setGigStyleName(binding.editText.stringContent())
                    }
                    findNavController().navigate(AddGigStyleFragmentDirections.actionAddGigStyleFragmentToAddGigDueDateFragment())
                }
            }
            GigFormType.AddGigDueDateFragment -> {
                binding.editText.setOnClickListener {
                    val newFragment = DatePickerFragment()
                    newFragment.setTargetFragment(this, REQUEST_CODE)
                    newFragment.show(fragmentManager!!, "datePicker")
                }
                binding.FormProceedButton.setOnClickListener {
                    findNavController().navigate(AddGigDueDateFragmentDirections.actionAddGigDueDateFragmentToAddGigPriceFragment())
                }
            }
            GigFormType.AddGigPriceFragment -> {
                binding.FormProceedButton.setOnClickListener {
                    if (!validateTextLayouts(binding.editText)) {
                       prefsValueHelper.setGigPrice(binding.editText.stringContent())
                    }
                    findNavController().navigate(
                        AddGigPriceFragmentDirections.actionAddGigPriceFragmentToAddGigDetails()
                    )
                }
            }
        }
    }


    protected abstract fun getGigFormType(): GigFormType

    protected abstract fun setDataHints(binding: FragmentBaseFormBinding)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            dateString = data!!.getStringExtra("selectedDate")!!
            dob = dateString
            binding.editText.setText(DateUtils.formatDateToDisplayDate(dateString))
            prefsValueHelper.setGigDueDate(dob)
        }

    }


}

