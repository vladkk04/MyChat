package com.arkul.mychat.ui.adapters.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.databinding.IncomingMessageItemBinding

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: IncomingMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatListAdapter.ViewHolder = ViewHolder(
        IncomingMessageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ChatListAdapter.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 0

}