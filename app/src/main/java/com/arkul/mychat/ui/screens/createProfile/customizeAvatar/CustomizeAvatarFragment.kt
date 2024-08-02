package com.arkul.mychat.ui.screens.createProfile.customizeAvatar

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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.arkul.mychat.R
import com.arkul.mychat.data.models.uiEvents.SelectAvatarModeEvents
import com.arkul.mychat.databinding.FragmentCustomizeAvatarBinding
import com.arkul.mychat.ui.adapters.viewPager2.SelectAvatarAdapter
import com.arkul.mychat.ui.screens.createProfile.SharedProfileViewModel
import com.arkul.mychat.utilities.customizeAvatar.BackgroundCardView
import com.arkul.mychat.utilities.cameraX.CameraPreview
import com.arkul.mychat.utilities.constants.UCropConstants
import com.arkul.mychat.utilities.image.ImageCropActivityResultContract
import com.arkul.mychat.utilities.permission.AndroidPermissions
import com.arkul.mychat.utilities.permission.PermissionViewModel
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CustomizeAvatarFragment : Fragment() {
    private var _binding: FragmentCustomizeAvatarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomizeAvatarViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val sharedViewModel: SharedProfileViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val permissionViewModel: PermissionViewModel by viewModels()

    private val cameraPreview: CameraPreview by lazy { CameraPreview(this) }

    private lateinit var imageCropActivity: ImageCropActivityResultContract

    private val singlePhotoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                imageCropActivity.startCropActivity(uri, "image_gallery") { output ->
                    viewModel.saveOriginalAvatarGalleryUri(it)
                    viewModel.setCurrentAvatarGalleryBitmap(output)
                }
            }
        }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupButtonsToggleModeListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomizeAvatarBinding.inflate(inflater, container, false)

        setupViewPager()
        setupImageCropActivity()
        setupBackgroundColorPicker()
        setupScrollerBackgroundColors()
        setupEditAvatarImageButtons()
        setupCameraPreviewCallBacks()

        binding.imageViewAvatar.setOnClickListener {
            shouldShowEditButtons()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                observeAvatarMode()
            }
            launch {
                observeCurrentAvatarBitmap().collectLatest { setAvatarImage(it) }
            }
            launch {
                observeBackgroundColor().collectLatest { setBackgroundColorAvatar(it) }
            }
            launch {
                observePermissionDialog().collectLatest {
                    it.forEach { permission ->
                       /* permissionDialog(
                            permissionDialog = AppPermissionDialogs.CAMERA,
                            !shouldShowRequestPermissionRationale(permission),
                            onDismissClick = { permissionViewModel.dismissDialog() },
                            onOkClick = {
                                permissionViewModel.launchSinglePermission(
                                    AndroidPermissions.LOCATION
                                )
                            },
                        )*/
                    }
                }
            }
        }

        return binding.root
    }

    private fun observeCurrentAvatarBitmap() = viewModel.currentAvatarBitmap

    private fun observeBackgroundColor() = viewModel.backgroundColor

    private fun observePermissionDialog() = permissionViewModel.dialogQueue

    private suspend fun observeAvatarMode() = viewModel.selectAvatarMode.collectLatest { mode ->
        hideEditButtons()

        when (mode) {
            SelectAvatarModeEvents.Camera -> {
                binding.nestedScrollableHost.visibility = View.INVISIBLE
                binding.scrollViewBackground.visibility = View.INVISIBLE
                binding.colorPicker.visibility = View.INVISIBLE
                binding.imageViewAvatar.visibility = View.VISIBLE
                binding.layoutCameraButtons.visibility = View.VISIBLE
            }

            SelectAvatarModeEvents.Default -> {
                binding.layoutCameraButtons.visibility = View.INVISIBLE
                binding.imageViewAvatar.visibility = View.INVISIBLE
                binding.nestedScrollableHost.visibility = View.VISIBLE
                binding.scrollViewBackground.visibility = View.VISIBLE
                binding.colorPicker.visibility = View.VISIBLE
            }

            SelectAvatarModeEvents.Gallery -> {
                binding.imageViewAvatar.setBackgroundColor(-13025722)
                binding.imageViewAvatar.visibility = View.VISIBLE
                binding.layoutCameraButtons.visibility = View.INVISIBLE
                binding.nestedScrollableHost.visibility = View.INVISIBLE
                binding.scrollViewBackground.visibility = View.INVISIBLE
                binding.colorPicker.visibility = View.INVISIBLE
            }

            null -> {
                return@collectLatest
            }
        }
    }

    private fun setAvatarImage(bitmap: Bitmap?) {
        bitmap?.let {
            binding.imageViewAvatar.setImageBitmap(it)
        }
    }

    private fun setBackgroundColorAvatar(color: Int) =
        binding.cardViewBackgroundAvatar.setCardBackgroundColor(color)

    private fun setupBackgroundColorPicker() {
        binding.colorPicker.setColorSelectionListener(object : SimpleColorSelectionListener() {
            override fun onColorSelected(color: Int) {
                viewModel.changeBackground(color)
            }
        })
    }

    private fun shouldShowEditButtons() {
        if (binding.imageViewAvatar.drawable == null) return
        if (binding.toggleMode.checkedButtonId == -1) return

        val isLayoutVisible =
            (binding.buttonChangeImageAvatar.visibility == View.VISIBLE && binding.buttonTransformImageAvatar.visibility == View.VISIBLE)

        val animationFadeIn =
            AnimationUtils.loadAnimation(requireContext(), androidx.appcompat.R.anim.abc_fade_in)
        val animationFadeOut =
            AnimationUtils.loadAnimation(requireContext(), androidx.appcompat.R.anim.abc_fade_out)

        when (binding.toggleMode.checkedButtonId) {
            R.id.button_camera_mode -> {
                if (viewModel.originalAvatarCameraUri.value == null) {
                    hideEditButtons()
                    return
                }
            }

            R.id.button_gallery_mode -> {
                if (viewModel.originalAvatarGalleryUri.value == null) {
                    hideEditButtons()
                    return
                }
            }

            R.id.button_default_mode -> {
                hideEditButtons()
                return
            }
        }

        when {
            isLayoutVisible -> {
                hideEditButtons()
                binding.buttonChangeImageAvatar.startAnimation(animationFadeOut)
                binding.buttonTransformImageAvatar.startAnimation(animationFadeOut)
            }

            else -> {
                showEditButtons()
                binding.buttonChangeImageAvatar.startAnimation(animationFadeIn)
                binding.buttonTransformImageAvatar.startAnimation(animationFadeIn)
            }
        }
    }

    private fun showEditButtons() {
        binding.buttonChangeImageAvatar.visibility = View.VISIBLE
        binding.buttonTransformImageAvatar.visibility = View.VISIBLE
    }

    private fun hideEditButtons() {
        binding.buttonChangeImageAvatar.visibility = View.INVISIBLE
        binding.buttonTransformImageAvatar.visibility = View.INVISIBLE
    }

    private fun setupButtonsToggleModeListener() {
        binding.toggleMode.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            when (checkedId) {
                R.id.button_camera_mode -> {
                    permissionViewModel.launchSinglePermission(AndroidPermissions.CAMERA)

                    if (viewModel.originalAvatarCameraUri.value == null) {
                        launchCameraPreview()
                    } else {
                        setAvatarImage(viewModel.currentAvatarCameraBitmap.value)
                    }

                    viewModel.selectAvatarMode(SelectAvatarModeEvents.Camera)
                }

                R.id.button_gallery_mode -> {
                    closeCameraPreview()
                    viewModel.selectAvatarMode(SelectAvatarModeEvents.Gallery)

                    if (viewModel.originalAvatarGalleryUri.value == null) {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    } else {
                        setAvatarImage(viewModel.currentAvatarGalleryBitmap.value)
                    }
                }

                R.id.button_default_mode -> {
                    closeCameraPreview()
                    viewModel.selectAvatarMode(SelectAvatarModeEvents.Default)
                }
            }
        }
    }

    private fun setupCameraPreviewCallBacks() {
        cameraPreview.addOnFlipCameraCallback(binding.buttonCameraSwitch)
        cameraPreview.addOnTakePictureCallback(binding.buttonTakePhoto) { output ->
            viewModel.saveOriginalAvatarCameraUri(output)
            imageCropActivity.startCropActivity(output, "image_camera") { cropOutput ->
                viewModel.setCurrentAvatarCameraBitmap(cropOutput)
                closeCameraPreview()
            }
        }
    }

    private fun launchCameraPreview() {
        hideEditButtons()
        cameraPreview.launchCameraPreview(binding.cameraPreviewView.surfaceProvider)
        binding.cameraPreviewView.visibility = View.VISIBLE
    }

    private fun closeCameraPreview() {
        cameraPreview.terminateCameraPreview()
        binding.layoutCameraButtons.visibility = View.INVISIBLE
        binding.cameraPreviewView.visibility = View.GONE
    }

    private fun setupEditAvatarImageButtons() {
        binding.buttonChangeImageAvatar.setOnClickListener {
            when (binding.toggleMode.checkedButtonId) {
                R.id.button_camera_mode -> {
                    binding.imageViewAvatar.visibility = View.INVISIBLE
                    binding.layoutCameraButtons.visibility = View.VISIBLE
                    launchCameraPreview()
                }

                R.id.button_gallery_mode -> {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            }
        }
        binding.buttonTransformImageAvatar.setOnClickListener {
            when (binding.toggleMode.checkedButtonId) {
                R.id.button_camera_mode -> {
                    viewModel.originalAvatarCameraUri.value?.let { input ->
                        imageCropActivity.startCropActivity(input, "image_camera") { output ->
                            viewModel.setCurrentAvatarCameraBitmap(output)
                        }
                    }
                }

                R.id.button_gallery_mode -> {
                    viewModel.originalAvatarGalleryUri.value?.let { input ->
                        imageCropActivity.startCropActivity(input, "image_gallery") { output ->
                            viewModel.setCurrentAvatarGalleryBitmap(output)
                        }
                    }
                }
            }
        }
    }

    private fun setupScrollerBackgroundColors() {
        val listOfDefaultBackgroundColors =
            resources.getIntArray(R.array.default_background_colors_avatar)

        listOfDefaultBackgroundColors.forEach { color ->
            val backgroundView =
                BackgroundCardView(requireContext()).createView(backgroundColor = color,
                    setOnClickListener = {
                        viewModel.changeBackground(it)
                        binding.colorPicker.setColor(it)
                    })
            binding.layoutBackgroundColors.addView(backgroundView)
        }
    }

    private fun setupImageCropActivity() {
        imageCropActivity = ImageCropActivityResultContract(this, UCropConstants.AVATAR_SETTINGS)
    }

    private fun setupViewPager() {
        val listOfDefaultAvatars = resources.obtainTypedArray(R.array.default_avatars)

        with(binding.viewPager) {
            adapter = SelectAvatarAdapter(listOfDefaultAvatars)
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    listOfDefaultAvatars.getDrawable(position)
                        ?.let { viewModel.setCurrentAvatarDefaultDrawable(it) }
                }
            })
            offscreenPageLimit = 3
            clipChildren = false
            clipToPadding = false
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setValidator { binding.toggleMode.checkedButtonId == R.id.button_default_mode || binding.imageViewAvatar.drawable != null }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
