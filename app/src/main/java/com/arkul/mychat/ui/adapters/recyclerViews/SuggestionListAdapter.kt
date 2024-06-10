package com.arkul.mychat.ui.adapters.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.data.models.Suggestion
import com.arkul.mychat.databinding.SuggestionItemBinding

class SuggestionListAdapter : RecyclerView.Adapter<SuggestionListAdapter.ViewHolder>() {

    private val asyncListDiffer = AsyncListDiffer(this, SuggestionListDifferCallbacks())

    fun saveData(list: List<Suggestion>) {
        asyncListDiffer.submitList(list)
    }

    //private val gg =

    inner class ViewHolder(private val binding: SuggestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(suggestion: Suggestion) {
            binding.textViewTopic.text = suggestion.topic
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SuggestionListAdapter.ViewHolder = ViewHolder(
        SuggestionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SuggestionListAdapter.ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

}