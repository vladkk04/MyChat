package com.arkul.mychat.ui.screens.bottomSheets

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkul.mychat.data.models.Suggestion
import com.arkul.mychat.data.models.inputLayouts.inputLayoutsEvents.CreateSuggestionInputLayoutEvents
import com.arkul.mychat.databinding.BottomSheetSuggestionCreateBinding
import com.arkul.mychat.ui.adapters.recyclerViews.SelectedPhotosGalleryListAdapter
import com.arkul.mychat.utilities.constants.Constants
import com.arkul.mychat.utilities.dialogs.SuggestionGalleryDialog
import com.arkul.mychat.utilities.dialogs.permissionDialog
import com.arkul.mychat.utilities.extensions.InputFilterType
import com.arkul.mychat.utilities.extensions.getFilter
import com.arkul.mychat.utilities.permission.AndroidPermissions
import com.arkul.mychat.utilities.permission.AppPermissionDialogs
import com.arkul.mychat.utilities.permission.PermissionViewModel
import com.arkul.mychat.utilities.permission.permissionViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SuggestionBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetSuggestionCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SelectedPhotosGalleryListAdapter

    private lateinit var suggestionGalleryDialog: SuggestionGalleryDialog

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private val viewModel: SuggestionBottomSheetViewModel by viewModels()

    private val permissionViewModel: PermissionViewModel by permissionViewModel()

    private var onCreateSuggestionListener: OnCreateSuggestionListener? = null

    companion object {
        const val TAG = "SuggestionBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSuggestionCreateBinding.inflate(inflater, container, false)

        setupUI()

        viewLifecycleOwner.lifecycleScope.launch {
            launch { observeInputLayoutEvents() }
            launch { observePermissionDialog() }
        }

        return binding.root
    }

    fun setOnCreateSuggestionListener(listener: OnCreateSuggestionListener) {
        this.onCreateSuggestionListener = listener
    }
    private fun checkPermission() {
        val permissions = AndroidPermissions.getMediaImagesPermission()

        val permissionStatus = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        AndroidPermissions.READ_MEDIA_IMAGES.permission
                    ) == PERMISSION_GRANTED -> false

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        AndroidPermissions.READ_MEDIA_VISUAL_USER_SELECTED.permission
                    ) == PERMISSION_GRANTED -> true

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2 &&
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        AndroidPermissions.READ_EXTERNAL_STORAGE.permission
                    ) == PERMISSION_GRANTED -> false

            else -> null
        }

        if (permissionStatus != null) {
            showSuggestionGalleryDialog(permissionStatus)
        } else {
            permissionViewModel.launchMultiplyPermission(
                permissions,
                onPermissionResult = { permission, granted ->
                    val isMediaLimitAccess =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
                                permission == AndroidPermissions.READ_MEDIA_VISUAL_USER_SELECTED && granted
                    showSuggestionGalleryDialog(isMediaLimitAccess)
                })
        }
    }

    private fun setupUI() {
        setupTextInputFilters()
        setupBottomSheet()
        setupRecycleView()
        setupButtonOnClickListeners()
        setupSuggestionGalleryDialog()
        setupInputTextChangeListeners()
        binding.maxSelectablePhotoFromGallerySuggestion.text =
            "max: ${Constants.MAX_SELECTABLE_PHOTOS_FROM_GALLERY_SUGGESTION_STRING}"
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.layoutSuggestionCreate)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setupRecycleView() {
        adapter = SelectedPhotosGalleryListAdapter()

        binding.recyclerViewSuggestionsPhotos.apply {
            adapter = this@SuggestionBottomSheet.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        adapter.setOnItemAddSelectPhotoView { checkPermission() }
        adapter.addButtonSelectPhotoView = true
    }

    private fun setupTextInputFilters() {
        binding.textInputDescriptionEditText.filters =
            getFilter(InputFilterType.IS_WHITE_SPACE)
    }

    private fun setupInputTextChangeListeners() {
        binding.textInputTopicEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onEventInputLayout(CreateSuggestionInputLayoutEvents.TopicChanged(text.toString()))
        }
        binding.textInputDescriptionEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onEventInputLayout(CreateSuggestionInputLayoutEvents.DescriptionChanged(text.toString()))
        }
    }

    private fun setupButtonOnClickListeners() {
        binding.buttonTooltipAnonymous.setOnClickListener { it.performLongClick() }

        binding.buttonCreateSuggestion.setOnClickListener {
            val newSuggestion = Suggestion(
                topic = binding.textInputTopicEditText.text.toString(),
                description = binding.textInputDescriptionEditText.text.toString(),
                isAnonymous = binding.checkboxAnonymous.isChecked,
                photos = adapter.asyncListDiffer.currentList.filterNotNull().map { it.uri }
            )

            val resultOfCreateSuggestion = viewModel.createSuggestion(newSuggestion)

            if (resultOfCreateSuggestion) {
                onCreateSuggestionListener?.onCreate(newSuggestion)
                dismiss()
            }
        }
    }

    private fun setupSuggestionGalleryDialog() {
        suggestionGalleryDialog = SuggestionGalleryDialog(this).apply {
            setOnAddPhotosClickListener { adapter.setList(it) }
            setOnMediaLimitAccessClickListener {
                permissionViewModel.launchMultiplyPermission(AndroidPermissions.getMediaImagesPermission(),
                    onPermissionCallback = {
                        needToRefreshList()
                    }
                )
            }
        }
    }

    private fun showSuggestionGalleryDialog(isMediaLimitAccess: Boolean) {
        suggestionGalleryDialog.createSuggestionGalleryDialog(isMediaLimitAccess)?.show()
    }

    private suspend fun observePermissionDialog() = permissionViewModel.dialogQueue.collectLatest {
        it.forEach { androidPermission ->
            permissionDialog(
                permissionDialog = AppPermissionDialogs.GALLERY(),
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(androidPermission.permission),
                onDismissClick = { permissionViewModel.dismissDialog() },
                onOkClick = {}
            )
        }
    }

    private suspend fun observeInputLayoutEvents() = viewModel.inputLayoutState.collectLatest {
        binding.textInputTopicLayout.error = it.topicError
        binding.textInputDescriptionLayout.error = it.descriptionError
    }

    fun interface OnCreateSuggestionListener {
        fun onCreate(suggestion: Suggestion)
    }
}