package com.arkul.mychat.ui.adapters.recyclerViews

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.arkul.mychat.R
import com.arkul.mychat.data.models.Suggestion
import com.arkul.mychat.databinding.SuggestionItemBinding
import com.bumptech.glide.Glide

class SuggestionListAdapter : BaseRecyclerView<Suggestion, SuggestionItemBinding>() {

    private var reportOnClickListener: ReportOnClickListener? = null
    private var thumbUpOnClickListener: ThumbOnClickListener? = null
    private var thumbDownOnClickListener: ThumbOnClickListener? = null
    private var forwardSuggestionOnClickListener: ForwardSuggestionOnClickListener? = null
    private var authorOnClickListener: AuthorOnClickListener? = null


    fun setOnReportClickListener(listener: ReportOnClickListener) {
        reportOnClickListener = listener
    }

    fun setOnAuthorClickListener(listener: AuthorOnClickListener) {
        authorOnClickListener = listener
    }

    fun setOnForwardSuggestionClickListener(listener: ForwardSuggestionOnClickListener) {
        forwardSuggestionOnClickListener = listener
    }

    fun setThumbOnClickListener(
        listenerUp: ThumbOnClickListener,
        listenerDown: ThumbOnClickListener
    ) {
        this.thumbUpOnClickListener = listenerUp
        this.thumbDownOnClickListener = listenerDown
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): SuggestionItemBinding =
        SuggestionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun getChangePayload(oldItem: Suggestion, newItem: Suggestion): Any? {
        return when {
            oldItem.topic != newItem.topic -> SuggestionPayload.Topic(newItem.topic)

            oldItem.thumbUpCount != newItem.thumbUpCount -> SuggestionPayload.ThumbUp(
                newItem.isThumbedUp,
                newItem.thumbUpCount
            )

            oldItem.thumbDownCount != newItem.thumbDownCount -> SuggestionPayload.ThumbDown(
                newItem.isThumbedDown,
                newItem.thumbDownCount
            )

            else -> null
        }
    }

    override fun bindPayload(
        item: Suggestion,
        binding: SuggestionItemBinding,
        payloads: MutableList<Any>
    ) {
        when (val payload = payloads.lastOrNull()) {
            is SuggestionPayload.Topic -> {
                binding.textViewTopic.text = payload.topic
            }

            is SuggestionPayload.ThumbDown -> {
                binding.buttonThumbDown.text = payload.count.toString()
                binding.buttonThumbDown.isChecked = payload.thumbedDown
            }

            is SuggestionPayload.ThumbUp -> {
                binding.buttonThumbUp.text = payload.count.toString()
                binding.buttonThumbUp.isChecked = payload.thumbedUp
            }
        }
    }

    override fun bind(item: Suggestion, binding: SuggestionItemBinding) {
        with(binding) {
            textViewTopic.text = item.topic
            buttonThumbUp.text = item.thumbUpCount.toString()
            buttonThumbDown.text = item.thumbDownCount.toString()
            buttonThumbUp.isChecked = item.isThumbedUp
            buttonThumbDown.isChecked = item.isThumbedDown

            val getIcon = if (item.isAnonymous) {
                BitmapFactory.decodeResource(root.resources, R.drawable.ic_anonymus_mask)
            } else {
                item.avatarAuthor
            }

            Glide.with(root.context)
                .load(getIcon)
                .override(128)
                .into(imageViewAuthor)

            buttonThumbUp.setOnCheckedChangeListener { _, isChecked ->
                thumbUpOnClickListener?.onClick()
            }

            buttonThumbDown.setOnCheckedChangeListener { _, isChecked ->
                thumbDownOnClickListener?.onClick()
            }

            buttonReport.setOnClickListener {
                reportOnClickListener?.onClick()
            }

            buttonForwardSuggestion.setOnClickListener {
                forwardSuggestionOnClickListener?.onClick()
            }

            imageViewAuthor.setOnClickListener {
                if (!item.isAnonymous) {
                    it.tooltipText = null
                    authorOnClickListener?.onClick()
                } else {
                    it.tooltipText =
                        ContextCompat.getString(binding.root.context, R.string.user_anonymous_text)
                    imageViewAuthor.performLongClick()
                }
            }
        }
    }

    private sealed class SuggestionPayload {
        data class Topic(val topic: String) : SuggestionPayload()
        data class ThumbUp(val thumbedUp: Boolean, val count: Int) : SuggestionPayload()
        data class ThumbDown(val thumbedDown: Boolean, val count: Int) : SuggestionPayload()
    }

    fun interface OnUiItemClickListener {
        fun onClick()
    }

    fun interface ReportOnClickListener : OnUiItemClickListener
    fun interface ThumbOnClickListener : OnUiItemClickListener
    fun interface AuthorOnClickListener : OnUiItemClickListener
    fun interface ForwardSuggestionOnClickListener : OnUiItemClickListener
}