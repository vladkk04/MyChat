package com.arkul.mychat.ui.fragments.waitingVerify

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arkul.mychat.R
import com.arkul.mychat.databinding.FragmentWaitingVerifyEmailBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WaitingVerifyEmail : Fragment() {

    private var _binding: FragmentWaitingVerifyEmailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WaitingVerifyViewModel by viewModels()

    //val navigation: NavigationObserver by lazy { NavigationObserver(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaitingVerifyEmailBinding.inflate(inflater, container, false)

        Glide.with(requireContext())
            .load(R.drawable.happy_emoji)
            .into(binding.smile)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                if (!it.isResendConfirmation) {
                    createTimer.start()
                    binding.buttonResendConfirmationEmail.isClickable = false
                    binding.buttonResendConfirmationEmail.setBackgroundColor(getColor(R.color.non_clickable_color))
                    binding.buttonResendConfirmationEmail.setTextColor(getColor(R.color.non_clickable_color_text))
                } else {
                    binding.buttonResendConfirmationEmail.isClickable = true
                    binding.buttonResendConfirmationEmail.setBackgroundColor(getColor(R.color.color_primary_night))
                    binding.buttonResendConfirmationEmail.setTextColor(getColor(R.color.color_on_primary_night))
                    binding.buttonResendConfirmationEmail.text = getString(R.string.resend_confirmation_text)
                }

                if (it.errorMessage != null) {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonResendConfirmationEmail.setOnClickListener {
            viewModel.setResendConfirmation(false)
        }

        return binding.root
    }

    private val createTimer = object : CountDownTimer((60 * 1000), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val secondsRemaining = (millisUntilFinished / 1000)
            if (secondsRemaining > 0) {
                binding.buttonResendConfirmationEmail.text = getString(
                    R.string.resend_confirmation_text_with_timer, secondsRemaining
                )
            }
        }

        override fun onFinish() {
            viewModel.setResendConfirmation(true)
        }
    }

    private fun getColor(color: Int): Int = ContextCompat.getColor(requireContext(), color)
}
