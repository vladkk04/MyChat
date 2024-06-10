package com.arkul.mychat.ui.adapters.recyclerViews

import androidx.recyclerview.widget.DiffUtil
import com.arkul.mychat.data.models.Message
import com.arkul.mychat.data.models.Suggestion

class ChatListDifferCallbacks: DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }

}