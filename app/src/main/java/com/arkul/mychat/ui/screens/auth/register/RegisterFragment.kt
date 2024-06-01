package com.arkul.mychat.ui.screens.auth.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arkul.mychat.data.models.AuthInputLayoutEvents
import com.arkul.mychat.data.models.RegisterTextLayoutState
import com.arkul.mychat.data.models.RegisterUiState
import com.arkul.mychat.data.network.firebase.auth.GitHubAuthUiClient
import com.arkul.mychat.data.network.firebase.auth.GoogleAuthUiClient
import com.arkul.mychat.databinding.FragmentRegisterBinding
import com.arkul.mychat.ui.navigation.BaseFragment
import com.arkul.mychat.utilities.extensions.showToast
import com.arkul.mychat.utilities.progressBar.showProgressBar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterViewModel>() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override val viewModel: RegisterViewModel by viewModels()

    private val googleAuth by lazy { GoogleAuthUiClient(requireContext()) }
    private val gitHubAuth by lazy { GitHubAuthUiClient(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupInputTextChangeListeners()
        setupButtonsListeners()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                observeUIState()
            }
            launch {
                observeUITextLayoutState()
            }
        }


        return binding.root
    }

    private suspend fun observeUITextLayoutState() =
        viewModel.stateTextLayout.collectLatest { updateTextLayoutsUI(it) }

    private suspend fun observeUIState() =
        viewModel.uiState.collectLatest { updateUI(it) }

    private fun updateUI(state: RegisterUiState) {
        showProgressBar(isShowing = state.isLoading)
        showToast(state.errorMessage)
    }

    private fun updateTextLayoutsUI(state: RegisterTextLayoutState) {
        binding.inputLayoutEmail.error = state.errorEmail
        binding.inputLayoutPassword.apply {
            this.error = state.errorPassword
            showEndIconPasswordInputLayout(state.password, this)
        }
        binding.inputLayoutPasswordRetype.apply {
            this.error = state.errorPasswordRepeated
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
            viewModel.onEvent(AuthInputLayoutEvents.EmailChanged(text.toString()))
        }
        binding.inputTextPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(AuthInputLayoutEvents.PasswordChanged(text.toString()))
        }
        binding.inputTextRetypePassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(AuthInputLayoutEvents.PasswordRepeatedChanged(text.toString()))
        }
    }

    private fun setupButtonsListeners() {
        binding.buttonRegister.setOnClickListener {
            showToast(viewModel.uiState.value.errorMessage)
            viewModel.signUpWithEmail()
        }
        binding.buttonGoogle.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.signUpWithCredential(googleAuth.getAuthCredential())
            }
        }
        binding.buttonGithub.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.signUpWithProvider(gitHubAuth.getAuthCredential())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}