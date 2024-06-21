package com.arkul.mychat.ui.screens.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.core.content.ContextCompat
import com.arkul.mychat.R
import com.arkul.mychat.databinding.BottomSheetSuggestionCreateBinding
import com.arkul.mychat.databinding.SelectedGalleryPhotoItemBinding
import com.arkul.mychat.utilities.constants.Constants
import com.arkul.mychat.utilities.dialogs.createSuggestionGalleryDialog
import com.arkul.mychat.utilities.extensions.InputFilterType
import com.arkul.mychat.utilities.extensions.getFilter
import com.arkul.mychat.utilities.extensions.getStateListDrawable
import com.arkul.mychat.utilities.extensions.toDp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SuggestionBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetSuggestionCreateBinding? = null
    private val binding get() = _binding!!

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
        _binding =  BottomSheetSuggestionCreateBinding.inflate(inflater, container, false)

        binding.buttonTooltipAnonymous.setOnClickListener { it.performLongClick() }

        binding.imageViewButtonSelectPhotoFromGallery.setOnClickListener {
            createSuggestionGalleryDialog { list ->
                val widthPhoto = 100
                val widthPhotoToDp = widthPhoto.toDp(requireContext())

                val getFillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_fill)
                val getOutlineDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_outline)

                binding.imageViewButtonSelectPhotoFromGallery.requestLayout()
                binding.imageViewButtonSelectPhotoFromGallery.layoutParams.width = widthPhotoToDp

                list.forEach { item ->
                    val bindingSelectedGalleryPhotoItem =
                        SelectedGalleryPhotoItemBinding.inflate(
                            inflater,
                            binding.layoutSuggestionPhotosAdd,
                            false
                        )

                    bindingSelectedGalleryPhotoItem.imageViewSelectedPhotoFromGallery.setImageURI(item.uri)-
                    bindingSelectedGalleryPhotoItem.checkboxDeleteSelectedPhoto.apply {
                        bindingSelectedGalleryPhotoItem.checkboxDeleteSelectedPhoto.visibility = View.VISIBLE
                        buttonDrawable = getStateListDrawable(getFillDrawable, getOutlineDrawable)
                    }
                    binding.layoutSuggestionPhotosAdd.addView(bindingSelectedGalleryPhotoItem.root)

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