package com.arkul.mychat.ui.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arkul.mychat.R
import com.arkul.mychat.data.models.AuthInputLayoutEvents
import com.arkul.mychat.data.models.LoginTextLayoutState
import com.arkul.mychat.data.models.LoginUiState
import com.arkul.mychat.data.network.firebase.auth.GitHubAuthUiClient
import com.arkul.mychat.data.network.firebase.auth.GoogleAuthUiClient
import com.arkul.mychat.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val googleAuth by lazy { GoogleAuthUiClient(requireContext()) }
    private val gitHubAuth by lazy { GitHubAuthUiClient(requireActivity()) }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupButtonsListeners()
        setupInputTextChangeListeners()

        lifecycleScope.launch {
            observeUITextLayoutState()
        }
        lifecycleScope.launch {
            observeUIState()
        }

        binding.textForgetPassword.setOnClickListener {
            viewModel.onForgetPassword()
            findNavController().navigate(R.id.action_initialFragment_to_forgetPasswordFragment2)
        }

        return binding.root
    }

    private suspend fun observeUITextLayoutState() =
        viewModel.stateTextLayout.collectLatest { updateTextLayoutsUI(it) }

    private suspend fun observeUIState() =
        viewModel.uiState.collectLatest { updateUI(it) }

    private fun updateUI(state: LoginUiState) {
        if (state.errorMessage != null) {
            Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateTextLayoutsUI(state: LoginTextLayoutState) {
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
                val credentialResult = googleAuth.getAuthCredential()
                viewModel.signInWithCredential(credentialResult)
            }
        }
        binding.buttonGithub.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val credentialResult = gitHubAuth.getAuthCredential()
                viewModel.signInWithCredential(credentialResult)
            }
        }
        binding.buttonLogin.setOnClickListener {
            viewModel.signInWithEmail()
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