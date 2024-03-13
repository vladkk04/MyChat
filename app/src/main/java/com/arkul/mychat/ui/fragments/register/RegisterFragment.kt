package com.arkul.mychat.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arkul.mychat.data.models.RegistrationFormState
import com.arkul.mychat.data.models.RegistrationFormState.RegistrationOnEvent
import com.arkul.mychat.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //.d("d", "create register")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupInputTextChangeListeners()
        setupButtonsListeners()

        lifecycleScope.launch {
            observeUIState()
        }

        return binding.root
    }

    private suspend fun observeUIState() = viewModel.stateTextLayout.collectLatest { updateTextLayoutsUI(it) }

    private fun updateTextLayoutsUI(state: RegistrationFormState) {
        binding.inputLayoutEmail.error = state.emailError
        binding.inputLayoutPassword.apply {
            this.error = state.passwordError
            showEndIconPasswordInputLayout(state.password, this)
        }
        binding.inputLayoutPasswordRetype.apply {
            this.error = state.passwordRepeatedError
            showEndIconPasswordInputLayout(state.passwordRepeated, this)
        }
    }

    private fun showEndIconPasswordInputLayout(value: String, textInputLayout: TextInputLayout) {
        if (value.isNotBlank()) {
            textInputLayout.endIconMode = END_ICON_PASSWORD_TOGGLE
        } else {
            textInputLayout.endIconMode = END_ICON_NONE
        }
    }

    private fun setupInputTextChangeListeners() {
        binding.inputTextEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(RegistrationOnEvent.EmailChanged(text.toString()))
        }
        binding.inputTextPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(RegistrationOnEvent.PasswordChanged(text.toString()))
        }
        binding.inputTextRetypePassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(RegistrationOnEvent.PasswordRepeatedChanged(text.toString()))
        }
    }

    private fun setupButtonsListeners() {
        binding.buttonRegister.setOnClickListener {
            viewModel.onEvent(RegistrationOnEvent.Submit)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}