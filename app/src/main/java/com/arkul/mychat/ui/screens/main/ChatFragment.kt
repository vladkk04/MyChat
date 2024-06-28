package com.arkul.mychat.ui.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkul.mychat.data.models.Message
import com.arkul.mychat.databinding.FragmentChatBinding
import com.arkul.mychat.databinding.FragmentChatsBinding
import com.arkul.mychat.ui.adapters.recyclerViews.ChatListAdapter

class ChatFragment: Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChatListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        adapter = ChatListAdapter()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        requireActivity()


        adapter.saveData(listOf(
            Message("hello"), Message("hello"), Message("hello"), Message("hello"), Message("hello"), Message("hello"),
            Message("hello"), Message("hello"), Message("hello"), Message("hello"), Message("hello"), Message("hello")
        ))

        return binding.root
    }
}