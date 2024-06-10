package com.arkul.mychat.ui.adapters.recyclerViews

import androidx.recyclerview.widget.DiffUtil
import com.arkul.mychat.data.models.Suggestion

class SuggestionListDifferCallbacks: DiffUtil.ItemCallback<Suggestion>() {
    override fun areItemsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
        return oldItem.topic == newItem.topic
    }

    override fun areContentsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
        return oldItem == newItem
    }

}