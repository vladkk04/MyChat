package com.arkul.mychat.ui.screens.createProfile.initBasicInfoProfile

import android.R.id.input
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.arkul.mychat.databinding.FragmentInitializationProfileFormBinding
import com.arkul.mychat.ui.screens.createProfile.SharedProfileViewModel
import com.arkul.mychat.utilities.date.createDatePicker
import com.arkul.mychat.utilities.date.getLocalDatePattern
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class InitializationProfileFormFragment : Fragment() {

    private var _binding: FragmentInitializationProfileFormBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedProfileViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private fun textChange(editText: TextInputEditText, block: (text: String) -> Unit) {
        editText.doOnTextChanged { text, _, _, _ ->
            block.invoke(text.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitializationProfileFormBinding.inflate(inflater, container, false)

        setupInputsEditTextChanges()
        setupDatePicker()

        viewLifecycleOwner.lifecycleScope.launch {
            observeBirthday().collectLatest {
                binding.inputEditTextBirthday.setText(it)
            }
        }

        return binding.root
    }

    private fun setupDatePicker() {
        binding.inputLayoutBirthday.helperText = getLocalDatePattern
        binding.inputEditTextBirthday.setOnClickListener { showDatePicker() }
    }

    private fun setupInputsEditTextChanges() {
        binding.inputEditTextUsername.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            source.toString().filterNot { it.isWhitespace() }
        })

        textChange(binding.inputEditTextFirstaname) {
            sharedViewModel.setFirstName(it)
        }

        textChange(binding.inputEditTextLastname) {
            sharedViewModel.setLastName(it)
        }

        textChange(binding.inputEditTextAboutMe) {
            sharedViewModel.setAboutMe(it)
        }

        textChange(binding.inputEditTextUsername) {
            sharedViewModel.setUserName("${binding.inputLayoutUsername.prefixText}$it")
        }


    }

    private fun observeBirthday() =
        sharedViewModel.birthdayFormatted.flowWithLifecycle(viewLifecycleOwner.lifecycle)

    private fun showDatePicker() {
        createDatePicker(
            onPositiveButtonCallBack = { selectedDate ->
                sharedViewModel.setBirthday(selectedDate, getLocalDatePattern)
            }).show(parentFragmentManager, "date-picker")
    }
}