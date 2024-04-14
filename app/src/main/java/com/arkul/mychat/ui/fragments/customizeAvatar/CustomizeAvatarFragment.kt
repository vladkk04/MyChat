package com.arkul.mychat.ui.fragments.customizeAvatar

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.R
import com.arkul.mychat.databinding.FragmentCustomizeAvatarBinding
import com.arkul.mychat.ui.adapters.viewPager2.SelectAvatarAdapter
import com.arkul.mychat.ui.fragments.createProfile.SharedProfileViewModel
import com.arkul.mychat.utilities.CreateBackgroundCardView
import com.madrapps.pikolo.HSLColorPicker
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CustomizeAvatarFragment : Fragment() {

    private var _binding: FragmentCustomizeAvatarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CustomizeAvatarViewModel by viewModels()

    private val sharedViewModel: SharedProfileViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomizeAvatarBinding.inflate(inflater, container, false)

        setupViewPager()
        setupBackgroundColorPicker()
        setupScrollerBackgroundColors()

        viewLifecycleOwner.lifecycleScope.launch {
            observeBackgroundColor()
        }

        return binding.root
    }

    private suspend fun observeBackgroundColor() =
        viewModel.backgroundColor.collectLatest {
            binding.cardViewBackgroundAvatar.setCardBackgroundColor(it)
        }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupBackgroundColorPicker() {
        val hslColorPicker = object : HSLColorPicker(requireContext()) {
            override fun onTouchEvent(event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sharedViewModel.changeUserPickColorState(true)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        sharedViewModel.changeUserPickColorState(false)
                    }
                    MotionEvent.ACTION_UP -> {
                        sharedViewModel.changeUserPickColorState(true)
                    }
                }
                return super.onTouchEvent(event)
            }
        }
        binding.colorPicker.setColorSelectionListener(object : SimpleColorSelectionListener() {
            override fun onColorSelected(color: Int) {
                viewModel.changeBackground(color)
            }
        })
        binding.colorPicker.setOnTouchListener { v, event ->
            hslColorPicker.onTouchEvent(event)
        }
    }


    private fun setupScrollerBackgroundColors() {
        val listOfDefaultBackgroundColors =
            resources.getIntArray(R.array.default_background_colors_avatar)

        listOfDefaultBackgroundColors.forEach { color ->
            val backgroundView = CreateBackgroundCardView(requireContext()).createView(
                backgroundColor = color,
                setOnClickListener = {
                    viewModel.changeBackground(it)
                    binding.colorPicker.setColor(it)
                }
            )
            binding.layoutBackgroundColors.addView(backgroundView)
        }
    }

    private fun setupViewPager() {
        with(binding.viewPager) {
            adapter = SelectAvatarAdapter(
                resources.obtainTypedArray(R.array.default_avatars)
            )
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            offscreenPageLimit = 3
            clipChildren = false
            clipToPadding = false
        }
    }
}
