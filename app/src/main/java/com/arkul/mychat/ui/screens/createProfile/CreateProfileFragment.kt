package com.arkul.mychat.ui.screens.createProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.arkul.mychat.data.models.ViewPagerNavigationEvent
import com.arkul.mychat.databinding.FragmentCreateProfileBinding
import com.arkul.mychat.ui.adapters.viewPager2.BaseFragmentStateAdapter
import com.arkul.mychat.ui.screens.createProfile.customizeAvatar.CustomizeAvatarFragment
import com.arkul.mychat.ui.screens.createProfile.initBasicInfoProfile.BasicInfoProfileFragment
import com.arkul.mychat.ui.screens.createProfile.profilePreview.ProfilePreviewFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CreateProfileFragment : Fragment() {

    private var _binding: FragmentCreateProfileBinding? = null
    private val binding get() = _binding!!

    private val fragmentCreatorList = arrayListOf(
        BasicInfoProfileFragment(),
        CustomizeAvatarFragment(),
        ProfilePreviewFragment()
    )

    private val sharedProfileViewModel: SharedProfileViewModel by viewModels()

    private val args: CreateProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProfileBinding.inflate(inflater, container, false)

        setupViewPager()
        setupButtonsNavigation()

        sharedProfileViewModel.setArguments(args)

        viewLifecycleOwner.lifecycleScope.launch {
            sharedProfileViewModel.viewPagerNavigationEvent.collectLatest {
                when (it) {
                    ViewPagerNavigationEvent.OnNextPage -> {
                        binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
                    }

                    ViewPagerNavigationEvent.OnBackPage -> {
                        binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1, true)
                    }
                }
            }
        }

        return binding.root
    }

    private fun setupButtonsNavigation() {
        binding.buttonNext.setOnClickListener {
            sharedProfileViewModel.onNavigatePage(ViewPagerNavigationEvent.OnNextPage)
        }
        binding.buttonBack.setOnClickListener {
            sharedProfileViewModel.onNavigatePage(ViewPagerNavigationEvent.OnBackPage)
        }
    }

    private fun setupViewPager() {
        with(binding.viewPager) {
            adapter = BaseFragmentStateAdapter(fragmentCreatorList, this@CreateProfileFragment)
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val getRadioButtonId = binding.radioGroup.getChildAt(position).id
                    binding.radioGroup.check(getRadioButtonId)
                    binding.buttonBack.visibility =
                        if (position >= 1) View.VISIBLE else View.INVISIBLE
                }
            })
            offscreenPageLimit = 3
            isUserInputEnabled = false
        }
    }



}