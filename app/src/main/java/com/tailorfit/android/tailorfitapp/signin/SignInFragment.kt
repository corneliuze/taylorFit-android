package com.tailorfit.android.tailorfitapp.signin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tailorfit.android.base.BaseFragment
import com.tailorfit.android.databinding.FragmentSignInBinding
import com.tailorfit.android.extensions.stringContent
import com.tailorfit.android.tailorfitapp.models.request.SignInRequest
import com.tailorfit.android.tailorfitapp.validateTextLayouts
import javax.inject.Inject


class SignInFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SignInViewModel

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)

        binding.signinButton.setOnClickListener {
            if (validateTextLayouts(
                    binding.phoneEditText,
                    binding.passwordEditText
                )
            ) {
                viewModel.signIn(
                    SignInRequest(
                        binding.passwordEditText.stringContent(),
                        binding.phoneEditText.stringContent().toLong()
                    )
                )
            }
        }

        viewModel.signInResponse.observe(this, Observer {
            if (it!= null){
              findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToAddCustomerFragment())
            }
        })
    }
}
