package com.arkul.mychat.ui.screens.bottomSheets

import android.app.Dialog
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import com.arkul.mychat.databinding.BottomSheetSuggestionCreateBinding
import com.arkul.mychat.utilities.extensions.InputFilterType
import com.arkul.mychat.utilities.extensions.getFilter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class SuggestionBottomSheet: BottomSheetDialogFragment() {
    private var _binding: BottomSheetSuggestionCreateBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "SuggestionBottomSheet"
    }

    private fun textChange(editText: TextInputEditText, block: (text: String) -> Unit) {
        editText.doOnTextChanged { text, _, _, _ ->
            var converted = text.toString()
            if(converted.contains("\n")){
                converted = converted.replace("\n", "");
            }
            block.invoke(converted)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetSuggestionCreateBinding.inflate(inflater, container, false)

        binding.buttonTooltipAnonymous.setOnClickListener { it.performLongClick() }


        binding.textInputEditTextDesctiptionSuggestion.filters = getFilter(InputFilterType.IS_WHITE_SPACE)

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