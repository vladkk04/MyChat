package com.arkul.mychat.ui.screens.forgetPassword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arkul.mychat.databinding.FragmentForgetPasswordBinding
import com.arkul.mychat.ui.navigation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment<ForgetPasswordViewModel>() {
    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val args: ForgetPasswordFragmentArgs by navArgs()

    override val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)

        setupButtonListener()
        binding.inputTextEmail.setText(args.email)

        return binding.root
    }


    private fun setupButtonListener() {
        binding.buttonSendResetPassword.setOnClickListener {
            viewModel.sendRestPassword()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}