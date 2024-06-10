package com.arkul.mychat.ui.navigation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.arkul.mychat.ui.navigation.models.NavigationEvent

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    is NavigationEvent.OnNavigateTo -> {
                        findNavController().navigate(event.directions, event.navOptions)
                    }

                    NavigationEvent.OnNavigateBack -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.navigationEvent.removeObservers(viewLifecycleOwner)
    }

}