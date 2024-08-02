package com.arkul.mychat.ui.adapters.recyclerViews

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.databinding.MessagePhotoItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


class MessagePhotoListAdapter : RecyclerView.Adapter<MessagePhotoListAdapter.ViewHolder>() {

    private var messagePhotoList: List<Uri> = emptyList()
    fun setMessagePhotoList(list: List<Uri>) {
        this.messagePhotoList = list
    }

    inner class ViewHolder(val binding: MessagePhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoUri: Uri) {
            binding.imageViewPhoto.setImageURI(photoUri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        MessagePhotoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = messagePhotoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(messagePhotoList[position])
}