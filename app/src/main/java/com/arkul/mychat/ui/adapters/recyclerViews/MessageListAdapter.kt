package com.arkul.mychat.ui.adapters.recyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.arkul.mychat.data.models.Message
import com.arkul.mychat.data.models.Message.TypeOfMessage
import com.arkul.mychat.databinding.MessageItemBinding

class MessageListAdapter: BaseRecyclerView<Message, MessageItemBinding>() {

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): MessageItemBinding =
        MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(item: Message, binding: MessageItemBinding) {
        val messagePhotoListAdapter = MessagePhotoListAdapter()
        messagePhotoListAdapter.setMessagePhotoList(item.photos)

        val layoutManager = GridLayoutManager(binding.root.context, 3).apply {
            val itemsCount = messagePhotoListAdapter.itemCount

            this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (itemsCount > 2) {
                        when (position % 3) {
                            0 -> 3
                            1 -> 2
                            2 -> 1
                            else -> getSpanSize(position)
                        }
                    } else {
                        3
                    }
                }
            }
        }

        with(binding) {
            textViewMessage.text = item.text
            textViewSendTimeMessage.text = item.time
            recyclerViewPhotos.adapter = messagePhotoListAdapter
            recyclerViewPhotos.layoutManager = layoutManager
            recyclerViewPhotos.setHasFixedSize(true)
            recyclerViewPhotos.isNestedScrollingEnabled = false
        }

        when (item.typeOfMessage) {
            TypeOfMessage.PHOTO_TEXT -> {
                binding.recyclerViewPhotos.visibility = View.VISIBLE
                binding.textViewMessage.visibility = View.VISIBLE
            }
            TypeOfMessage.PHOTO -> {
                binding.recyclerViewPhotos.visibility = View.VISIBLE
                binding.textViewMessage.visibility = View.INVISIBLE
            }
            TypeOfMessage.TEXT -> {
                binding.recyclerViewPhotos.visibility = View.GONE
                binding.textViewMessage.visibility = View.VISIBLE
            }

            null -> {
                return
            }
        }


    }
}
