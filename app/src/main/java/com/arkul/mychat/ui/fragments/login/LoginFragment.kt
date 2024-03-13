package com.arkul.mychat.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arkul.mychat.data.network.firebase.AuthMethod
import com.arkul.mychat.data.network.firebase.signIn.GoogleAuthUiClient
import com.arkul.mychat.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val googleAuth by lazy { GoogleAuthUiClient(requireContext()) }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupButtonsListeners();

        binding.inputTextPassword.doOnTextChanged { text, _, _, _ ->
            if (text?.isNotBlank() == true) {
                binding.inputLayoutPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            } else {
                binding.inputLayoutPassword.endIconMode = TextInputLayout.END_ICON_NONE
            }
        }
        return binding.root
    }

    private fun setupButtonsListeners() {
        binding.buttonGoogle.setOnClickListener {
            lifecycleScope.launch {
                viewModel.signIn(AuthMethod.Google(googleAuth.getCredential()))
            }
        }
        binding.buttonGithub.setOnClickListener {
            viewModel.signIn(AuthMethod.GitHub(requireActivity()))
        }
        binding.buttonLogin.setOnClickListener {
            viewModel.signIn(AuthMethod.Email)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}