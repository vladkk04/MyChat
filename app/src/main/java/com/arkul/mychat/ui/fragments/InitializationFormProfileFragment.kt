package com.arkul.mychat.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arkul.mychat.databinding.FragmentInitializationFormProfileBinding

class InitializationFormProfileFragment: Fragment() {

    private var _binding: FragmentInitializationFormProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitializationFormProfileBinding.inflate(inflater, container, false)

        return binding.root
    }
}