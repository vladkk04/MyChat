package com.arkul.mychat.ui.screens.initial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.NavDirections
import androidx.viewpager2.widget.ViewPager2
import com.arkul.mychat.R
import com.arkul.mychat.databinding.FragmentInitialBinding
import com.arkul.mychat.ui.adapters.viewPager2.BaseFragmentStateAdapter
import com.arkul.mychat.ui.adapters.viewPager2.TabLayoutCornerRadiusAnimator
import com.arkul.mychat.ui.screens.auth.login.LoginFragment
import com.arkul.mychat.ui.screens.auth.register.RegisterFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitialFragment : Fragment() {
    private var _binding: FragmentInitialBinding? = null
    private val binding get() = _binding!!

    private val fragmentCreatorList = arrayListOf(LoginFragment(), RegisterFragment())

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: BaseFragmentStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        viewPagerAdapter = BaseFragmentStateAdapter(fragmentCreatorList as ArrayList<Fragment>, this)

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

