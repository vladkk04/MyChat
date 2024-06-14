package com.arkul.mychat.ui.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkul.mychat.R
import com.arkul.mychat.data.models.Message
import com.arkul.mychat.databinding.FragmentChatsBinding
import com.arkul.mychat.ui.adapters.recyclerViews.ChatListAdapter

class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChatListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)

        adapter = ChatListAdapter()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        adapter.saveData(listOf(Message("hello"), Message("hello"), Message("hello"), Message("hello"), Message("hello"), Message("hello"),Message("hello"), Message("hello"), Message("hello"), Message("hello"), Message("hello"), Message("hello")))

        return binding.root
    }
}