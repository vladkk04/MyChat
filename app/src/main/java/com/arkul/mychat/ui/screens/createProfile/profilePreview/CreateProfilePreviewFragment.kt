package com.arkul.mychat.ui.screens.createProfile.profilePreview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arkul.mychat.databinding.FragmentCreateProfilePreviewBinding
import com.arkul.mychat.ui.screens.createProfile.SharedProfileViewModel
import com.arkul.mychat.utilities.image.ImageCropActivityResultContract
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CreateProfilePreviewFragment : Fragment() {

    private var _binding: FragmentCreateProfilePreviewBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedProfileViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val viewModel: ProfilePreviewViewModel by viewModels()

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
        _binding = FragmentCreateProfilePreviewBinding.inflate(inflater, container, false)

        setupEditWallpaperOnClickListener()
        setupWallpaperOnClickListener()
        setupImageCropActivity()

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                observeCroppedWallpaper().collectLatest { binding.wallpaperImage.setImageBitmap(it) }
            }
            launch {
                observeFullName().collectLatest { binding.textViewFullName.text = it }
            }
            launch {
                observeUserName().collectLatest { binding.textViewUsername.text = it }
            }
            launch {
                observeBirthday().collectLatest { binding.textViewBirthday.text = it }
            }
            launch {
                observeAboutMe().collectLatest { binding.textViewAboutMe.text = it }
            }
            launch {
                observeCurrentAvatarUri().collectLatest { setupAvatarImage(it) }
            }
            launch {
                observeBackgroundColor().collectLatest {
                    binding.imageViewAvatar.setImageURI(null)
                    binding.imageViewAvatar.setBackgroundColor(it)
                }
            }
        }

        return binding.root
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

    private fun observeCroppedWallpaper() = viewModel.croppedWallpaperBitmap

    private fun observeBackgroundColor() = sharedViewModel.backgroundColor

    private fun observeFullName() = sharedViewModel.fullName

    private fun observeUserName() = sharedViewModel.userName

    private fun observeBirthday() = sharedViewModel.birthdayFormatted

    private fun observeAboutMe() = sharedViewModel.aboutMe

    private fun observeCurrentAvatarUri() = sharedViewModel.currentAvatarUri

    private fun setupAvatarImage(bitmap: Bitmap?) {
        binding.imageViewAvatar.setImageBitmap(bitmap)
    }

}