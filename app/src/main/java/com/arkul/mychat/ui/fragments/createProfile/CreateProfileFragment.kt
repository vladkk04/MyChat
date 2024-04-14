package com.arkul.mychat.ui.fragments.createProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.arkul.mychat.databinding.FragmentCreateProfileBinding
import com.arkul.mychat.ui.adapters.viewPager2.BaseFragmentStateAdapter
import com.arkul.mychat.ui.fragments.InitializationFormProfileFragment
import com.arkul.mychat.ui.fragments.customizeAvatar.CustomizeAvatarFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CreateProfileFragment: Fragment() {

    private var _binding: FragmentCreateProfileBinding? = null
    private val binding get() = _binding!!

    private val fragmentCreatorList = arrayListOf(InitializationFormProfileFragment(), CustomizeAvatarFragment())

    private val sharedProfileViewModel: SharedProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProfileBinding.inflate(inflater, container, false)

        setupViewPager()

        lifecycleScope.launch {
            sharedProfileViewModel.isUserPickColor.collectLatest {
                binding.viewPager.isUserInputEnabled = it
            }
        }

        return binding.root
    }

    private fun setupViewPager() {
        with(binding.viewPager) {
            adapter = BaseFragmentStateAdapter(fragmentCreatorList, this@CreateProfileFragment)
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val getRadioButtonId = binding.radioGroup.getChildAt(position).id
                    binding.radioGroup.check(getRadioButtonId)
                }
            })
            offscreenPageLimit = 3
        }
    }

}