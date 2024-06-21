package com.arkul.mychat.ui.screens.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.core.view.marginLeft
import com.arkul.mychat.databinding.BottomSheetSuggestionCreateBinding
import com.arkul.mychat.utilities.constants.Constants
import com.arkul.mychat.utilities.dialogs.createSuggestionGalleryDialog
import com.arkul.mychat.utilities.extensions.InputFilterType
import com.arkul.mychat.utilities.extensions.getFilter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SuggestionBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetSuggestionCreateBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "SuggestionBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetSuggestionCreateBinding.inflate(inflater, container, false)

        binding.buttonTooltipAnonymous.setOnClickListener { it.performLongClick() }

        binding.imageViewButtonSelectPhotoFromGallery.setOnClickListener {
            createSuggestionGalleryDialog { list ->
                binding.scrollViewSuggestionsPhotos.visibility = View.VISIBLE
                list.forEach { item ->
                    val image = ImageView(requireContext())
                    image.layoutParams = ViewGroup.LayoutParams(100, 100)
                    image.setPadding(10,0,10,0)
                    image.setImageURI(item.uri)
                    image.scaleType = ImageView.ScaleType.CENTER_CROP
                    binding.layoutSuggestionPhotos.addView(image, 100, 100)
                }
            }?.show()
        }

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