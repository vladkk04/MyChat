package com.arkul.mychat.ui.screens.createProfile.customizeAvatar

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.R
import com.arkul.mychat.databinding.FragmentCustomizeAvatarBinding
import com.arkul.mychat.ui.adapters.viewPager2.SelectAvatarAdapter
import com.arkul.mychat.ui.screens.createProfile.SharedProfileViewModel
import com.arkul.mychat.utilities.BackgroundCardView
import com.arkul.mychat.utilities.cameraX.CameraPreview
import com.arkul.mychat.utilities.dialogs.permissionDialog
import com.arkul.mychat.utilities.image.ImageCropActivityResultContract
import com.arkul.mychat.utilities.openAppSettings
import com.arkul.mychat.utilities.permission.AndroidPermissions
import com.arkul.mychat.utilities.permission.AppPermissionDialogs
import com.arkul.mychat.utilities.permission.PermissionViewModel
import com.arkul.mychat.utilities.permission.permissionViewModel
import com.madrapps.pikolo.HSLColorPicker
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CustomizeAvatarFragment : Fragment() {

    companion object {
        const val TOGGLE_MODE_SAVED_STATE = "toggle_mode_saved_state"
    }

    private var _binding: FragmentCustomizeAvatarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomizeAvatarViewModel by viewModels()

    private val permissionViewModel: PermissionViewModel by permissionViewModel()

    private val sharedViewModel: SharedProfileViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val cameraPreview: CameraPreview by lazy { CameraPreview(this) }

    private lateinit var imageCropActivity: ImageCropActivityResultContract

    private val singlePhotoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                sharedViewModel.saveOriginalAvatarGalleryUri(it)
                imageCropActivity.startCropActivity(uri, "image_gallery") { output ->
                    sharedViewModel.setCurrentAvatarGalleryBitmap(output)
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
            showEditButtons()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                observeAvatarGalleryUri().collectLatest {
                    setAvatarImage(it)
                }
            }
            launch {
                observeAvatarCameraUri().collectLatest {
                    setAvatarImage(it)
                }
            }
            launch {
                observeBackgroundColor().collectLatest {
                    sharedViewModel.setBackgroundColor(it)
                    setBackgroundColorAvatar(it)
                }
            }
            launch {
                observePermissionDialog().collectLatest {
                    it.forEach { permission ->
                        permissionDialog(
                            permission = AppPermissionDialogs.CAMERA,
                            !shouldShowRequestPermissionRationale(AndroidPermissions.LOCATION.permission),
                            onDismissClick = { permissionViewModel.dismissDialog() },
                            onOkClick = {
                                permissionViewModel.launchMultiplyPermission(
                                    AndroidPermissions.LOCATION
                                )
                            },
                            onGoToAppSettingsClick = ::openAppSettings
                        )
                    }
                }
            }
        }

        return binding.root
    }

    private fun observeAvatarGalleryUri() = sharedViewModel.currentAvatarGalleryBitmap

    private fun observeAvatarCameraUri() = sharedViewModel.currentAvatarCameraBitmap

    private fun observeBackgroundColor() = viewModel.backgroundColor

    private fun observePermissionDialog() = permissionViewModel.dialogQueue

    private fun setAvatarImage(bitmap: Bitmap?) {
        binding.imageViewAvatar.setImageBitmap(bitmap)
    }

    private fun setBackgroundColorAvatar(color: Int) =
        binding.cardViewBackgroundAvatar.setCardBackgroundColor(color)

    @SuppressLint("ClickableViewAccessibility")
    private fun setupBackgroundColorPicker() {
        val hslColorPicker = object : HSLColorPicker(requireContext()) {
            override fun onTouchEvent(event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sharedViewModel.changeUserInputEnabledViewPagerState(true)
                    }

                    MotionEvent.ACTION_MOVE -> {
                        sharedViewModel.changeUserInputEnabledViewPagerState(false)
                    }

                    MotionEvent.ACTION_UP -> {
                        sharedViewModel.changeUserInputEnabledViewPagerState(true)
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

    private fun showEditButtons() {
        if (binding.imageViewAvatar.drawable == null) return
        if (binding.toggleMode.checkedButtonId == -1) return

        val isLayoutVisible =
            (binding.buttonChangeImageAvatar.visibility == View.VISIBLE && binding.buttonTransformImageAvatar.visibility == View.VISIBLE)

        val animationFadeIn =
            AnimationUtils.loadAnimation(requireContext(), androidx.appcompat.R.anim.abc_fade_in)
        val animationFadeOut =
            AnimationUtils.loadAnimation(requireContext(), androidx.appcompat.R.anim.abc_fade_out)

        when (binding.toggleMode.checkedButtonId) {
            R.id.button_default_mode -> {
                hideEditButtonsVisibility()
                return
            }

            R.id.button_gallery_mode -> {
                if (sharedViewModel.originalAvatarGalleryUri.value == null) {
                    hideEditButtonsVisibility()
                    return
                }
            }

            R.id.button_camera_mode -> {
                if (sharedViewModel.originalAvatarCameraUri.value == null) {
                    hideEditButtonsVisibility()
                    return
                }
            }
        }


        when {
            isLayoutVisible -> {
                hideEditButtonsVisibility()
                binding.buttonChangeImageAvatar.startAnimation(animationFadeOut)
                binding.buttonTransformImageAvatar.startAnimation(animationFadeOut)
            }

            else -> {
                showEditButtonsVisibility()
                binding.buttonChangeImageAvatar.startAnimation(animationFadeIn)
                binding.buttonTransformImageAvatar.startAnimation(animationFadeIn)
            }
        }
    }

    private fun showEditButtonsVisibility() {
        binding.buttonChangeImageAvatar.visibility = View.VISIBLE
        binding.buttonTransformImageAvatar.visibility = View.VISIBLE
    }

    private fun hideEditButtonsVisibility() {
        binding.buttonChangeImageAvatar.visibility = View.INVISIBLE
        binding.buttonTransformImageAvatar.visibility = View.INVISIBLE
    }

    private fun setupButtonsToggleModeListener() {
        //binding.toggleMode.clearChecked()

        binding.toggleMode.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            hideEditButtonsVisibility()
            binding.imageViewAvatar.setImageDrawable(null)

            when (checkedId) {
                R.id.button_camera_mode -> {
                    binding.imageViewAvatar.visibility = View.VISIBLE
                    binding.layoutCameraButtons.visibility = View.VISIBLE
                    binding.nestedScrollableHost.visibility = View.INVISIBLE
                    binding.scrollViewBackground.visibility = View.INVISIBLE
                    binding.colorPicker.visibility = View.INVISIBLE

                    permissionViewModel.launchSinglePermission(AndroidPermissions.CAMERA)

                    if (sharedViewModel.originalAvatarCameraUri.value == null) {
                        launchCameraPreview()
                    } else {
                        binding.layoutCameraButtons.visibility = View.INVISIBLE
                        setAvatarImage(sharedViewModel.currentAvatarCameraBitmap.value)
                    }
                }

                R.id.button_gallery_mode -> {

                    binding.imageViewAvatar.visibility = View.VISIBLE
                    binding.layoutCameraButtons.visibility = View.INVISIBLE
                    binding.nestedScrollableHost.visibility = View.INVISIBLE
                    binding.scrollViewBackground.visibility = View.INVISIBLE
                    binding.colorPicker.visibility = View.INVISIBLE

                    closeCameraPreview()

                    if (sharedViewModel.originalAvatarGalleryUri.value == null) {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    } else {
                        setAvatarImage(sharedViewModel.currentAvatarGalleryBitmap.value)
                    }
                }

                R.id.button_default_mode -> {
                    binding.layoutCameraButtons.visibility = View.INVISIBLE
                    binding.imageViewAvatar.visibility = View.INVISIBLE
                    binding.nestedScrollableHost.visibility = View.VISIBLE
                    binding.scrollViewBackground.visibility = View.VISIBLE
                    binding.colorPicker.visibility = View.VISIBLE

                    closeCameraPreview()
                }
            }
        }
    }

    private fun setupCameraPreviewCallBacks() {
        cameraPreview.addOnFlipCameraCallback(binding.buttonCameraSwitch)
        cameraPreview.addOnTakePictureCallback(binding.buttonTakePhoto) { output ->
            sharedViewModel.saveOriginalAvatarCameraUri(output)
            imageCropActivity.startCropActivity(output, "image_camera") { cropOutput ->
                sharedViewModel.setCurrentAvatarCameraBitmap(cropOutput)
                closeCameraPreview()
            }
        }
    }

    private fun launchCameraPreview() {
        hideEditButtonsVisibility()
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
                    sharedViewModel.originalAvatarCameraUri.value?.let { input ->
                        imageCropActivity.startCropActivity(input, "image_camera") { output ->
                            sharedViewModel.setCurrentAvatarCameraBitmap(output)
                        }
                    }
                }

                R.id.button_gallery_mode -> {
                    sharedViewModel.originalAvatarGalleryUri.value?.let { input ->
                        imageCropActivity.startCropActivity(input, "image_gallery") { output ->
                            sharedViewModel.setCurrentAvatarGalleryBitmap(output)
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
        imageCropActivity = ImageCropActivityResultContract(this, viewModel.uCropSettings)
    }

    private fun setupViewPager() {
        val listOfDefaultAvatars = resources.obtainTypedArray(R.array.default_avatars)

        with(binding.viewPager) {
            adapter = SelectAvatarAdapter(listOfDefaultAvatars)
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            offscreenPageLimit = 3
            clipChildren = false
            clipToPadding = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
