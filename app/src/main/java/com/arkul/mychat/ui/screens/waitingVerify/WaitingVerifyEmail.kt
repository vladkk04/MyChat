package com.arkul.mychat.ui.screens.waitingVerify

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arkul.mychat.R
import com.arkul.mychat.databinding.FragmentWaitingVerifyEmailBinding
import com.arkul.mychat.ui.navigation.BaseFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WaitingVerifyEmail : BaseFragment<WaitingVerifyViewModel>() {

    private var _binding: FragmentWaitingVerifyEmailBinding? = null
    private val binding get() = _binding!!

    override val viewModel: WaitingVerifyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaitingVerifyEmailBinding.inflate(inflater, container, false)

        setupResendConfirmationButtonListener()
        setupEmoji()

        viewLifecycleOwner.lifecycleScope.launch {
            observeUIState()
        }

        return binding.root
    }

    private fun setupEmoji() {
        Glide.with(requireContext())
            .load(R.drawable.happy_emoji)
            .into(binding.smile)
    }

    private fun setupResendConfirmationButtonListener() {
        binding.buttonResendConfirmationEmail.setOnClickListener {
            viewModel.shouldResendConfirmation()
        }
    }

    private suspend fun observeUIState() {
        viewModel.uiState.collectLatest {
            if (!it.userHasConfirmed) { viewModel.sendEmailVerification() }
            if (!it.shouldResendConfirmation) { getTimer.start() }

            with(it.shouldResendConfirmation) {
                binding.buttonResendConfirmationEmail.isClickable = this
                binding.buttonResendConfirmationEmail.setBackgroundColor(
                    if (this) getColor(R.color.color_primary_night) else getColor(
                        R.color.non_clickable_color_night
                    )
                )
                binding.buttonResendConfirmationEmail.setTextColor(
                    if (this) getColor(R.color.color_on_primary_night) else getColor(
                        R.color.non_clickable_color_text_night
                    )
                )
                binding.buttonResendConfirmationEmail.text =
                    if (this) getString(R.string.resend_confirmation_text) else getString(
                        R.string.resend_confirmation_text_with_timer,
                        60
                    )
            }

            if (it.errorMessage != null) {
                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val getTimer = object : CountDownTimer((60 * 1000), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val secondsRemaining = (millisUntilFinished / 1000)
            if (secondsRemaining > 0) {
                binding.buttonResendConfirmationEmail.text = getString(
                    R.string.resend_confirmation_text_with_timer, secondsRemaining
                )
            }
        }

        override fun onFinish() {
            viewModel.shouldResendConfirmation(true)
        }
    }

    private fun getColor(color: Int): Int = ContextCompat.getColor(requireContext(), color)

    override fun onDestroy() {
        super.onDestroy()
        //viewModel.singOut()
    }
}
