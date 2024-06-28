package com.arkul.mychat.ui.screens.bottomSheets

import android.Manifest
import android.Manifest.permission.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkul.mychat.R
import com.arkul.mychat.databinding.BottomSheetSuggestionCreateBinding
import com.arkul.mychat.ui.adapters.recyclerViews.SelectedPhotosGalleryListAdapter
import com.arkul.mychat.utilities.constants.Constants
import com.arkul.mychat.utilities.dialogs.createSuggestionGalleryDialog
import com.arkul.mychat.utilities.dialogs.permissionDialog
import com.arkul.mychat.utilities.extensions.InputFilterType
import com.arkul.mychat.utilities.extensions.getFilter
import com.arkul.mychat.utilities.openAppSettings
import com.arkul.mychat.utilities.permission.AndroidPermissions
import com.arkul.mychat.utilities.permission.AppPermissionDialogs
import com.arkul.mychat.utilities.permission.PermissionViewModel
import com.arkul.mychat.utilities.permission.PermissionViewModelFactory
import com.arkul.mychat.utilities.permission.permissionViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SuggestionBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetSuggestionCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SelectedPhotosGalleryListAdapter

    private val permissionViewModel: PermissionViewModel by permissionViewModel()

    private val recyclerView by lazy { binding.recyclerViewSuggestionsPhotos }

    companion object {
        const val TAG = "SuggestionBottomSheet"
    }

    override fun getTheme(): Int {
        return R.style.ThemeOverlay_MyChat_BottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetSuggestionCreateBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            observePermissionDialog()
        }

        setupButtonOnClickListeners()
        setupAdapterGallery()
        setupRecycleView()
        setupBottomSheet()
        setupTextInputFilters()

        binding.maxSelectablePhotoFromGallerySuggestion.text =
            "max: ${Constants.MAX_SELECTABLE_PHOTOS_FROM_GALLERY_SUGGESTION_STRING}"

        return binding.root
    }

    private fun setupTextInputFilters() {
        binding.textInputEditTextDesctiptionSuggestion.filters =
            getFilter(InputFilterType.IS_WHITE_SPACE)
    }

    private fun setupBottomSheet() {
        val standardBottomSheet = binding.layoutSuggestionCreate
        val standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setupAdapterGallery() {
        adapter = SelectedPhotosGalleryListAdapter()
        adapter.addButtonSelectPhotoView = true
        adapter.setOnItemAddSelectPhotoView {
            permissionViewModel.launchMultiplyPermission(AndroidPermissions.getMediaPermission())
            createSuggestionGalleryDialog(true) {
                adapter.setList(it)
            }?.show()
        }
    }

    private fun setupRecycleView() {
        val linearLayoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
    }

    private fun setupButtonOnClickListeners() {
        binding.buttonTooltipAnonymous.setOnClickListener { it.performLongClick() }
    }

    private suspend fun observePermissionDialog() = permissionViewModel.dialogQueue.collectLatest {
        it.forEach { androidPermission ->
            permissionDialog(
                permissionDialog = AppPermissionDialogs.GALLERY(),
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(androidPermission.permission),
                onDismissClick = { permissionViewModel.dismissDialog() },
                onOkClick = {

                },
            )
        }
    }
}