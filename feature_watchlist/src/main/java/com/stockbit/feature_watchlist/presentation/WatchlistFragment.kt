package com.stockbit.feature_watchlist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.base.BaseViewModel
import com.stockbit.feature_watchlist.databinding.WatchlistFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class WatchlistFragment: BaseFragment() {
    private val viewModel by viewModel<WatchlistViewModel>()

    private var _binding: WatchlistFragmentBinding? = null
    private val binding get() = _binding!!

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = WatchlistFragmentBinding.inflate(inflater)
        return binding.root
    }
}