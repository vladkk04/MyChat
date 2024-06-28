package com.arkul.mychat.ui.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arkul.mychat.R
import com.arkul.mychat.databinding.FragmentProfileBinding
import com.arkul.mychat.utilities.constants.Constants
import com.arkul.mychat.utilities.constants.UCropConstants
import com.arkul.mychat.utilities.image.ImageCropActivityResultContract

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageCropActivity: ImageCropActivityResultContract

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        imageCropActivity = ImageCropActivityResultContract(this, UCropConstants.WALLPAPER_SETTINGS)

        binding.imageViewWallpaper.setOnClickListener {
            binding.buttonChangeImageWallpaper.apply {
                if (visibility == View.VISIBLE) {
                    visibility = View.INVISIBLE
                    animation = Constants.getAnimationFadeOut(requireContext())
                } else {
                    visibility = View.VISIBLE
                    animation = Constants.getAnimationFadeIn(requireContext())
                }
                setOnClickListener {
                    //TODO UCropScaling
                }
            }
        }

        binding.imageViewAvatar.setOnClickListener {
            binding.buttonChangeImageAvatar.apply {
                if (visibility == View.VISIBLE) {
                    visibility = View.INVISIBLE
                    animation = Constants.getAnimationFadeOut(requireContext())
                } else {
                    visibility = View.VISIBLE
                    animation = Constants.getAnimationFadeIn(requireContext())
                }
                setOnClickListener {
                    //TODO UCropScaling
                }
            }
        }


        return binding.root
    }
}