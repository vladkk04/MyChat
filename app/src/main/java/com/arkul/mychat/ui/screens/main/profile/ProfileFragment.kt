package com.arkul.mychat.ui.screens.main.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arkul.mychat.data.models.inputLayouts.inputLayoutsEvents.EditProfileInputLayoutEvents
import com.arkul.mychat.databinding.FragmentProfileBinding
import com.arkul.mychat.utilities.constants.Constants
import com.arkul.mychat.utilities.constants.UCropConstants
import com.arkul.mychat.utilities.image.ImageCropActivityResultContract
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageCropActivity: ImageCropActivityResultContract

    private val viewModel: ProfileViewModel by viewModels()

    private var isWallpaper = false

    private val fileName = if (isWallpaper) "wallpaper" else "avatar"

    private val singlePhotoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                imageCropActivity.startCropActivity(uri, fileName) { output ->
                    if (isWallpaper) {
                        viewModel.updateWallpaper(output)
                    } else {
                        viewModel.updateAvatar(output)
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        imageCropActivity = ImageCropActivityResultContract(this)

        setupOnClickListenerWallpaper()
        setupOnClickListenerAvatar()
        setupEditProfile()

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                observeUiState()
            }
            launch {
                observeInputLayoutState()
            }
        }

        return binding.root
    }

    private fun setupOnClickListenerWallpaper() {
        binding.cardViewWallpaper.setOnClickListener {
            binding.buttonChangeImageWallpaper.apply {
                if (visibility == View.VISIBLE) {
                    visibility = View.INVISIBLE
                    animation = Constants.getAnimationFadeOut(requireContext())
                } else {
                    visibility = View.VISIBLE
                    animation = Constants.getAnimationFadeIn(requireContext())
                }
                setOnClickListener {
                    isWallpaper = true
                    imageCropActivity.setUCropOptions(UCropConstants.WALLPAPER_SETTINGS)
                    singlePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            }
        }
    }

    private fun setupOnClickListenerAvatar() {
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
                    isWallpaper = false
                    imageCropActivity.setUCropOptions(UCropConstants.AVATAR_SETTINGS)
                    singlePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            }
        }
    }

    private fun setupEditProfile() {
        with(binding) {
            checkBoxSettingsProfile.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    textInputLayoutFullNameEditText.setText(textViewFullName.text)
                    textInputLayoutNicknameEditText.setText(textViewUsername.text)
                }

                toggleVisibility(
                    textInputLayoutFullNameLayout,
                    textInputLayoutNicknameLayout,
                    textViewUsername,
                    textViewFullName
                )
            }

            textInputLayoutFullNameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.inputLayoutEvent(EditProfileInputLayoutEvents.FullName(text.toString()))
            }

            textInputLayoutNicknameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.inputLayoutEvent(EditProfileInputLayoutEvents.NickName(text.toString()))
            }

            buttonSaveProfile.setOnClickListener {
                viewModel.updateProfileInfo()

                if (viewModel.inputLayoutState.value.errorFullName == null &&
                    viewModel.inputLayoutState.value.errorNickName == null
                ) {
                    it.visibility = View.GONE
                    binding.checkBoxSettingsProfile.isChecked = false
                }
            }
        }
    }

    private fun toggleVisibility(vararg views: View) {
        views.forEach { view ->
            view.visibility = if (view.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

    private suspend fun observeUiState() = viewModel.uiState.collectLatest { uiState ->
        with(uiState) {
            binding.textViewFullName.text = fullName
            binding.textViewUsername.text = nickName
            binding.wallpaperImage.setImageBitmap(wallpaper)
            binding.imageViewAvatar.setImageBitmap(avatar)
        }
    }

    private suspend fun observeInputLayoutState() =
        viewModel.inputLayoutState.collectLatest { inputLayoutState ->
            with(inputLayoutState) {
                binding.textInputLayoutFullNameLayout.error = errorFullName
                binding.textInputLayoutNicknameLayout.error = errorNickName

                binding.buttonSaveProfile.visibility =
                    if (fullName == viewModel.uiState.value.fullName &&
                        nickName == viewModel.uiState.value.nickName
                    ) View.GONE else View.VISIBLE
            }
        }
}