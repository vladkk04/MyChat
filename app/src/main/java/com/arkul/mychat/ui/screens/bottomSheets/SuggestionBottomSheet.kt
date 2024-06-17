package com.arkul.mychat.ui.screens.bottomSheets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.arkul.mychat.databinding.BottomSheetSuggestionCreateBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SuggestionBottomSheet: BottomSheetDialogFragment() {
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

        val standardBottomSheet = binding.layoutSuggestionCreate
        standardBottomSheet.layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT
        val standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)


        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)

    }

}