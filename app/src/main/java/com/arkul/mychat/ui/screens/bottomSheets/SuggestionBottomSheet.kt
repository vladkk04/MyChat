package com.arkul.mychat.ui.screens.bottomSheets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.R
import com.arkul.mychat.databinding.BottomSheetSuggestionCreateBinding
import com.arkul.mychat.ui.adapters.recyclerViews.SelectedPhotosGalleryListAdapter
import com.arkul.mychat.utilities.constants.Constants
import com.arkul.mychat.utilities.dialogs.createSuggestionGalleryDialog
import com.arkul.mychat.utilities.extensions.InputFilterType
import com.arkul.mychat.utilities.extensions.getFilter
import com.arkul.mychat.utilities.extensions.toDp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SuggestionBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetSuggestionCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SelectedPhotosGalleryListAdapter

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

        binding.buttonTooltipAnonymous.setOnClickListener { it.performLongClick() }

        val recyclerViewSuggestionsPhotos = binding.recyclerViewSuggestionsPhotos

        adapter = SelectedPhotosGalleryListAdapter()
        adapter.addButtonSelectPhotoView = true
        adapter.setOnItemAddSelectPhotoView {
            createSuggestionGalleryDialog {
                adapter.setList(it)
            }?.show()
        }

        recyclerViewSuggestionsPhotos.adapter = adapter
        recyclerViewSuggestionsPhotos.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSuggestionsPhotos.setHasFixedSize(true)

        binding.maxSelectablePhotoFromGallerySuggestion.text =
            "max: ${Constants.MAX_SELECTABLE_PHOTOS_FROM_GALLERY_SUGGESTION_STRING}"

        binding.textInputEditTextDesctiptionSuggestion.filters =
            getFilter(InputFilterType.IS_WHITE_SPACE)

        val standardBottomSheet = binding.layoutSuggestionCreate
        val standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        return binding.root
    }
}