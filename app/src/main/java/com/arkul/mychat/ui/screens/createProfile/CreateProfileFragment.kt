package com.arkul.mychat.ui.screens.createProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.arkul.mychat.databinding.FragmentCreateProfileBinding
import com.arkul.mychat.ui.adapters.viewPager2.BaseFragmentStateAdapter
import com.arkul.mychat.ui.screens.createProfile.profilePreview.CreateProfilePreviewFragment
import com.arkul.mychat.ui.screens.createProfile.initBasicInfoProfile.InitializationProfileFormFragment
import com.arkul.mychat.ui.screens.createProfile.customizeAvatar.CustomizeAvatarFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CreateProfileFragment: Fragment() {

    private var _binding: FragmentCreateProfileBinding? = null
    private val binding get() = _binding!!

    private val fragmentCreatorList = arrayListOf(InitializationProfileFormFragment(), CustomizeAvatarFragment(), CreateProfilePreviewFragment())

    private val sharedProfileViewModel: SharedProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProfileBinding.inflate(inflater, container, false)

        setupViewPager()
        setupButtonsNavigation()

        lifecycleScope.launch {
            sharedProfileViewModel.isUserInputEnabledViewPager.collectLatest {
                binding.viewPager.isUserInputEnabled = it
            }
        }

        return binding.root
    }

    private fun setupButtonsNavigation() {
        binding.buttonBack.setOnClickListener {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1, true)
        }
        binding.buttonNext.setOnClickListener {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
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
                    binding.buttonBack.visibility = if (position >= 1) View.VISIBLE else View.INVISIBLE
                }
            })
            offscreenPageLimit = 4
        }
    }

}