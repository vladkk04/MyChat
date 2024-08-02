package com.arkul.mychat.ui.adapters.carousels

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.R
import com.arkul.mychat.databinding.ImageCarouselItemBinding
import com.bumptech.glide.Glide

class CarouselAdapter : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    private var list: List<Uri> = emptyList()

    fun setList(list: List<Uri>)  {
        this.list = list
    }

    inner class ViewHolder(val binding: ImageCarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri) {
            Glide.with(binding.root)
                .load(uri)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ImageCarouselItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}