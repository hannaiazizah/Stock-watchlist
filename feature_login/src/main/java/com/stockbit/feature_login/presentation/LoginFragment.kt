package com.stockbit.feature_login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.base.BaseViewModel
import com.stockbit.feature_login.databinding.LoginFragmentBinding
import com.stockbit.navigation.NavigationFlow
import com.stockbit.navigation.ToFlowNavigatable
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment() {
    private val viewModel by viewModel<LoginViewModel>()

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = LoginFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            if (binding.edtUsername.editableText.toString().isEmpty()) {
                binding.edtUsername.error = "Username is empty"
            } else if (binding.edtInputPassword.editableText.toString().isEmpty()) {
                binding.edtInputPassword.error = "Password is empty"
            } else {
                (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.WatchlistFlow)
            }
        }
    }
}