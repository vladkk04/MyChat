package com.arkul.mychat.ui.adapters.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.data.models.Message
import com.arkul.mychat.data.models.Suggestion
import com.arkul.mychat.databinding.MessageItemBinding
import com.arkul.mychat.databinding.SuggestionItemBinding

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    private val asyncListDiffer = AsyncListDiffer(this, ChatListDifferCallbacks())

    fun saveData(list: List<Message>) {
        asyncListDiffer.submitList(list)
    }


    inner class ViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(suggestion: Message) {
            //binding.textViewTopic.text = suggestion.topic
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatListAdapter.ViewHolder = ViewHolder(
        MessageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ChatListAdapter.ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

}