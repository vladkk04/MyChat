package com.arkul.mychat.ui.screens.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arkul.mychat.data.models.inputLayouts.inputLayoutsEvents.AuthInputLayoutEvents
import com.arkul.mychat.data.models.inputLayouts.inputLayoutsState.LoginInputLayoutState
import com.arkul.mychat.data.models.uiStates.LoginUiState

import com.arkul.mychat.data.network.firebase.auth.GitHubAuthUiClient
import com.arkul.mychat.data.network.firebase.auth.GoogleAuthUiClient
import com.arkul.mychat.databinding.FragmentLoginBinding
import com.arkul.mychat.ui.navigation.BaseFragment
import com.arkul.mychat.utilities.extensions.showToast
import com.arkul.mychat.utilities.keyboard.hideKeyboardFrom
import com.arkul.mychat.utilities.progressBar.showProgressBar
import com.google.android.material.textfield.TextInputLayout
import com.vanniktech.ui.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val googleAuth by lazy { GoogleAuthUiClient(requireContext()) }
    private val gitHubAuth by lazy { GitHubAuthUiClient(requireActivity()) }

    override val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupButtonsListeners()
        setupInputTextChangeListeners()

        viewLifecycleOwner.lifecycleScope.launch {
            observeUITextLayoutState()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                observeUIState()
            }
        }

        binding.textForgetPassword.setOnClickListener {
            viewModel.navigateToForgetPassword()
        }

        return binding.root
    }

    private suspend fun observeUITextLayoutState() =
        viewModel.stateTextLayout.collectLatest { updateTextLayoutsUI(it) }

    private suspend fun observeUIState() =
        viewModel.uiState.collectLatest { updateUI(it) }

    private fun updateUI(state: LoginUiState) {
        showProgressBar(isShowing = state.isLoading)
        showToast(state.errorMessage)
    }

    private fun updateTextLayoutsUI(state: LoginInputLayoutState) {
        binding.inputLayoutEmail.error = state.errorEmail
        binding.inputLayoutPassword.apply {
            this.error = state.errorPassword
            showEndIconPasswordInputLayout(state.password, this)
        }
    }

    private fun setupInputTextChangeListeners() {
        binding.inputTextEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(AuthInputLayoutEvents.EmailChanged(text.toString()))
        }
        binding.inputTextPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(AuthInputLayoutEvents.PasswordChanged(text.toString()))
        }
    }

    private fun setupButtonsListeners() {
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
        binding.buttonLogin.setOnClickListener {
            if(viewModel.uiState.value.isLoading) return@setOnClickListener
            viewModel.signInWithEmail()
            requireActivity().hideKeyboard(binding.inputTextEmail.rootView)
            //hideKeyboardFrom(binding.inputTextPassword.rootView)
        }
    }

    private fun showEndIconPasswordInputLayout(value: String, textInputLayout: TextInputLayout) {
        if (value.isNotBlank()) {
            textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        } else {
            textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}