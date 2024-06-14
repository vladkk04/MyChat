package com.arkul.mychat.ui.adapters.recyclerViews

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.data.models.Suggestion
import com.arkul.mychat.databinding.SuggestionItemBinding

class SuggestionListAdapter : RecyclerView.Adapter<SuggestionListAdapter.ViewHolder>() {

    private var thumbUpOnClickListener: ThumbOnClickListener? = null
    private var thumbDownOnClickListener: ThumbOnClickListener? = null
    private var reportOnClickListener: ReportOnClickListener? = null

    private val asyncListDiffer = AsyncListDiffer(this, SuggestionListDifferCallbacks())
    private fun increaseThumbCount(charSequence: CharSequence): String =
        charSequence.toString().toInt().plus(1).toString()

    private fun decreaseThumbCount(charSequence: CharSequence): String =
        charSequence.toString().toInt().minus(1).toString()

    private fun handleThumbButtonClick(
        thumbButton: CheckBox,
        otherThumbButton: CheckBox,
    ) {
        with(otherThumbButton) {
            if (isChecked) {
                text = decreaseThumbCount(text)
                isChecked = false
            }
        }

        with(thumbButton) {
            text = if (isChecked) {
                increaseThumbCount(text)
            } else {
                decreaseThumbCount(text)
            }
        }
    }

    fun saveData(list: List<Suggestion>) {
        asyncListDiffer.submitList(list)
    }

    fun setOnReportClickListener(listener: ReportOnClickListener) {
        reportOnClickListener = listener
    }

    fun setThumbOnClickListener(
        listenerUp: ThumbOnClickListener,
        listenerDown: ThumbOnClickListener
    ) {
        this.thumbUpOnClickListener = listenerUp
        this.thumbDownOnClickListener = listenerDown
    }

    inner class ViewHolder(private val binding: SuggestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(suggestion: Suggestion) {
            with(binding) {
                textViewTopic.text = suggestion.topic
                
                binding.buttonReport.setOnClickListener {
                    reportOnClickListener?.onClick()
                }

                buttonThumbUp.setOnClickListener {
                    thumbUpOnClickListener?.onClick()
                    handleThumbButtonClick(buttonThumbUp, buttonThumbDown)
                }
                buttonThumbDown.setOnClickListener {
                    thumbDownOnClickListener?.onClick()
                    handleThumbButtonClick(buttonThumbDown, buttonThumbUp)
                }
            }
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

    fun interface ThumbOnClickListener {
        fun onClick()
    }

    fun interface ReportOnClickListener {
        fun onClick()
    }
}