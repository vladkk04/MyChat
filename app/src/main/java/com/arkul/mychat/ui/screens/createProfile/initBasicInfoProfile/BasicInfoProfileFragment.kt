package com.arkul.mychat.ui.screens.createProfile.initBasicInfoProfile

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arkul.mychat.data.models.BasicInfoProfileInputLayoutEvents
import com.arkul.mychat.databinding.FragmentBasicInfoProfileBinding
import com.arkul.mychat.ui.screens.createProfile.SharedProfileViewModel
import com.arkul.mychat.utilities.date.createDatePicker
import com.arkul.mychat.utilities.date.getLocalDatePattern
import com.arkul.mychat.utilities.extensions.InputFilterType
import com.arkul.mychat.utilities.extensions.getFilter
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BasicInfoProfileFragment : Fragment() {

    private var _binding: FragmentBasicInfoProfileBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedProfileViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val viewModel: BasicInfoProfileViewModel by viewModels(ownerProducer = { requireParentFragment() })

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
    ): View {
        _binding = FragmentBasicInfoProfileBinding.inflate(inflater, container, false)

        setupInputsEditTextChanges()
        setupDatePicker()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.args.collectLatest {
                    it?.let {
                        it.displayName?.split(" ").let { name ->
                            binding.inputEditTextFirstname.setText(name?.get(0))
                            binding.inputEditTextLastname.setText(name?.get(1))
                        }
                        binding.inputEditTextUsername.setText(it.username)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                observeUIState()
            }
        }

        return binding.root
    }


    private fun setupDatePicker() {
        binding.inputLayoutBirthday.helperText = getLocalDatePattern
        binding.inputEditTextBirthday.setOnClickListener { showDatePicker() }
    }

    private fun setupInputsEditTextChanges() {
        binding.inputEditTextFirstname.filters = getFilter(InputFilterType.IS_WHITE_SPACE)
        binding.inputEditTextLastname.filters = getFilter(InputFilterType.IS_WHITE_SPACE)
        binding.inputEditTextUsername.filters = getFilter(InputFilterType.IS_WHITE_SPACE)

        textChange(binding.inputEditTextFirstname) {
            viewModel.onEvent(BasicInfoProfileInputLayoutEvents.FirstName(it))
        }

        textChange(binding.inputEditTextLastname) {
            viewModel.onEvent(BasicInfoProfileInputLayoutEvents.LastName(it))
        }

        textChange(binding.inputEditTextUsername) {
            viewModel.onEvent(BasicInfoProfileInputLayoutEvents.UserName(it))
        }

        textChange(binding.inputEditTextAboutMe) {
            viewModel.onEvent(BasicInfoProfileInputLayoutEvents.AboutMe(it))
        }
    }

    private suspend fun observeUIState() = viewModel.stateTextLayout.collectLatest {
        binding.inputLayoutFirstName.error = it.firstNameError
        binding.inputLayoutLastName.error = it.lastNameError
        binding.inputLayoutUsername.error = it.usernameError
        binding.inputEditTextBirthday.setText(it.birthday)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setValidator { viewModel.isValidatedInputs() }
    }

    private fun showDatePicker() {
        createDatePicker(
            onPositiveButtonCallBack = { selectedDate ->
                viewModel.onEvent(
                    BasicInfoProfileInputLayoutEvents.Birthdate(
                        selectedDate,
                        getLocalDatePattern
                    )
                )
            }).show(parentFragmentManager, "date-picker")
    }
}