package com.arkul.mychat.ui.screens.createProfile.profilePreview

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arkul.mychat.data.models.SelectAvatarModeEvents
import com.arkul.mychat.databinding.FragmentProfilePreviewBinding
import com.arkul.mychat.ui.screens.createProfile.customizeAvatar.CustomizeAvatarViewModel
import com.arkul.mychat.ui.screens.createProfile.initBasicInfoProfile.BasicInfoProfileViewModel
import com.arkul.mychat.utilities.constants.Constants
import com.arkul.mychat.utilities.image.ImageCropActivityResultContract
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfilePreviewFragment : Fragment() {

    private var _binding: FragmentProfilePreviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfilePreviewViewModel by viewModels()

    private val basicInfoProfileViewModel: BasicInfoProfileViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val customizeAvatarViewModel: CustomizeAvatarViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var imageCropActivity: ImageCropActivityResultContract

    private val singlePhotoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                viewModel.saveOriginalWallpaperUri(it)
                imageCropActivity.startCropActivity(uri, "image_wallpaper") { output ->
                    viewModel.setCroppedWallpaperBitmap(output)
                    binding.buttonTransformImageWallpaper.visibility = View.VISIBLE
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilePreviewBinding.inflate(inflater, container, false)

        setupEditWallpaperOnClickListener()
        setupWallpaperOnClickListener()
        setupImageCropActivity()

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                observeBasicInfoProfile()
            }
            launch {
                observeAvatarMode()
            }
            launch {
                observeCroppedWallpaper()
            }
            launch {
                observeBackgroundColor()
            }
        }

        return binding.root
    }

    private fun setAvatarDrawable(drawable: Drawable?) {
        binding.imageViewAvatar.setImageDrawable(drawable)
    }

    private fun setAvatarBitmap(bitmap: Bitmap?) {
        binding.imageViewAvatar.setImageBitmap(bitmap)
    }

    private fun setupImageCropActivity() {
        imageCropActivity = ImageCropActivityResultContract(this, viewModel.uCropSettings)
    }

    private fun setupEditWallpaperOnClickListener() {
        binding.buttonTransformImageWallpaper.setOnClickListener {
            viewModel.originalWallpaperUri.value?.let {
                imageCropActivity.startCropActivity(
                    it,
                    "image_wallpaper"
                ) { output ->
                    viewModel.setCroppedWallpaperBitmap(output)
                }
            }
        }
    }

    private fun setupWallpaperOnClickListener() {
        binding.imageViewWallpaper.setOnClickListener {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
    }

    private suspend fun observeBasicInfoProfile() = basicInfoProfileViewModel.stateTextLayout.collectLatest {
        binding.textViewFullName.text = it.firstName.plus(" ").plus(it.lastName)
        binding.textViewUsername.text = Constants.USERNAME_TAG.plus(it.username)
        binding.textViewBirthday.text = it.birthday
        binding.textViewAboutMe.text = it.aboutMe
    }

    private suspend fun observeAvatarMode() =
        customizeAvatarViewModel.selectAvatarMode.collectLatest {
            when (it) {
                SelectAvatarModeEvents.Default -> {
                    binding.imageViewAvatar.post {
                        binding.imageViewAvatar.setContentPadding(60, 60, 60 , 40)
                    }.let { result ->
                        if(result) observeAvatarDefault()
                    }

                }

                null -> return@collectLatest

                else -> {
                    binding.imageViewAvatar.post {
                        binding.imageViewAvatar.setContentPadding(0, 0, 0 , 0)
                    }.let { result ->
                        if(result) observeCurrentAvatarBitmap()
                    }
                }
            }
        }

    private suspend fun observeBackgroundColor() = customizeAvatarViewModel.backgroundColor.collectLatest { binding.imageViewAvatar.setBackgroundColor(it) }

    private suspend fun observeCroppedWallpaper() = viewModel.croppedWallpaperBitmap.collectLatest { binding.wallpaperImage.setImageBitmap(it) }

    private suspend fun observeAvatarDefault() = customizeAvatarViewModel.currentAvatarDefault.collectLatest { setAvatarDrawable(it) }

    private suspend fun observeCurrentAvatarBitmap() = customizeAvatarViewModel.currentAvatarBitmap.collectLatest { setAvatarBitmap(it) }

}
