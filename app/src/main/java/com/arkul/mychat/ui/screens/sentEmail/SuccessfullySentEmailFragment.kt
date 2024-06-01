package com.arkul.mychat.ui.screens.sentEmail


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.arkul.mychat.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class SuccessfullySentEmailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_successfully_sent_email, container, false).apply {
            val button: Button = this.findViewById(R.id.sent_email_button_ok)

            button.setOnClickListener {
                findNavController().navigate(
                    SuccessfullySentEmailFragmentDirections.actionSuccessfullySentEmailFragmentToInitialFragment(),
                )
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                findNavController().popBackStack(R.id.initialFragment, false)
            }
        }
    }


}