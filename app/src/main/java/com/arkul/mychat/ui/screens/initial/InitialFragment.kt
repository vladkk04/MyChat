package com.arkul.mychat.ui.screens.initial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.arkul.mychat.R
import com.arkul.mychat.databinding.FragmentInitialBinding
import com.arkul.mychat.ui.adapters.viewPager2.BaseFragmentStateAdapter
import com.arkul.mychat.ui.adapters.viewPager2.TabLayoutCornerRadiusAnimator
import com.arkul.mychat.ui.navigation.BaseFragment
import com.arkul.mychat.ui.screens.auth.login.LoginFragment
import com.arkul.mychat.ui.screens.auth.register.RegisterFragment
import com.arkul.mychat.utilities.dialogs.createAlertDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InitialFragment : BaseFragment<InitialViewModel>() {
    private var _binding: FragmentInitialBinding? = null
    private val binding get() = _binding!!

    private val fragmentCreatorList = arrayListOf(LoginFragment(), RegisterFragment())

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: BaseFragmentStateAdapter

    override val viewModel: InitialViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        viewPagerAdapter =
            BaseFragmentStateAdapter(fragmentCreatorList as ArrayList<Fragment>, this)

        with(viewPager) {
            registerOnPageChangeCallback(TabLayoutCornerRadiusAnimator(tabLayout))
            adapter = viewPagerAdapter
            offscreenPageLimit = 1
            isSaveEnabled = true
            setupTabLayoutMediator().attach()
        }


        return binding.root
    }


    private fun setupTabLayoutMediator() =
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (fragmentCreatorList[position]) {
                is LoginFragment -> {
                    tab.text = "LOGIN"
                }

                is RegisterFragment -> {
                    tab.text = "REGISTER"
                }
            }
        }



    override fun onDestroy() {
        super.onDestroy()
        setupTabLayoutMediator().detach()
        _binding = null
    }
}

