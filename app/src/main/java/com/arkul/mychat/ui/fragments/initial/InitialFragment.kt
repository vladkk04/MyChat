package com.arkul.mychat.ui.fragments.initial

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels

import com.arkul.mychat.databinding.FragmentInitialBinding
import com.arkul.mychat.ui.adapters.viewPager2.InitialViewPagerAdapter
import com.arkul.mychat.ui.fragments.login.LoginFragment
import com.arkul.mychat.ui.fragments.register.RegisterFragment
import com.arkul.mychat.utilities.TabLayoutCornerRadiusAnimator
import com.arkul.mychat.utilities.TypeOfPermission
import com.arkul.mychat.utilities.openAppSettings
import com.arkul.mychat.utilities.permissionDialog
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitialFragment : Fragment() {
    private var _binding : FragmentInitialBinding? = null
    private val binding get() = _binding!!

    private val initialViewModel: InitialViewModel by viewModels()
    private val fragmentsViewPagerList = arrayListOf(LoginFragment(), RegisterFragment())

    companion object {
        const val REQUEST_PERMISSION = Manifest.permission.READ_SMS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)
        binding.viewPager2.adapter = InitialViewPagerAdapter(fragmentsViewPagerList, requireActivity())
        binding.viewPager2.registerOnPageChangeCallback(TabLayoutCornerRadiusAnimator(binding.tabLayout, 20))
        viewPager2SetupWithTabs().attach()

        return binding.root
    }

    private fun viewPager2SetupWithTabs(): TabLayoutMediator {
        return TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Login"
                1 -> tab.text = "Register"
            }
        }
    }

    /*private val requestPermissionResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(!isGranted) showPermissionDialog()
    }*/

   /* private fun showPermissionDialog() {
        permissionDialog(
            permission = TypeOfPermission.SMS,
            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(REQUEST_PERMISSION),
            onOkClick = { requestPermissionResultLauncher.launch(REQUEST_PERMISSION) },
            onGoToAppSettingsClick = { openAppSettings() }
        )
    }*/

    override fun onDestroy() {
        super.onDestroy()
        viewPager2SetupWithTabs().detach()
        _binding = null
        Log.d("destroy", "Initial")
        //requestPermissionResultLauncher.unregister()
    }

}