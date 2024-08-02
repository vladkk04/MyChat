package com.arkul.mychat.ui.screens.main.inboxChats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkul.mychat.databinding.FragmentInboxChatsBinding
import com.arkul.mychat.ui.adapters.recyclerViews.ChatListAdapter

class InboxChatsFragment : Fragment() {

    private var _binding: FragmentInboxChatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChatListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInboxChatsBinding.inflate(inflater, container, false)

        adapter = ChatListAdapter()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }
}