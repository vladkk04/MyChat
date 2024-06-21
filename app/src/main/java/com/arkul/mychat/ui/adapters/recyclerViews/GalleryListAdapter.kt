package com.arkul.mychat.ui.adapters.recyclerViews

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.data.models.PhotoGallery
import com.arkul.mychat.databinding.SelectGalleryPhotoItemBinding
import com.bumptech.glide.Glide

class GalleryListAdapter() : RecyclerView.Adapter<GalleryListAdapter.ViewHolder>() {

    private var list: ArrayList<PhotoGallery> = arrayListOf()
    val selectedPhotos: ArrayList<PhotoGallery> = arrayListOf()

    private var selectPhotoCounter = 0

    private var listener: OnItemClickListener? = null

    fun setList(list: ArrayList<PhotoGallery>) {
        this.list = list
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: SelectGalleryPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PhotoGallery) {
            val counter = binding.textViewSelectablePhotoCounter
            val imageView = binding.imageViewSelectPhotoFromGallery

            Glide.with(binding.root.context)
                .asBitmap()
                .override(300, 300)
                .load(model.uri)
                .into(imageView)

            imageView.setOnClickListener {
                if (counter.visibility == View.VISIBLE) {
                    selectPhotoCounter--
                    selectedPhotos.remove(model)
                    counter.visibility = View.INVISIBLE
                } else {
                    selectPhotoCounter++
                    selectedPhotos.add(model)
                    counter.visibility = View.VISIBLE
                }
                counter.text = selectPhotoCounter.toString()
                listener?.onItemClick(selectedPhotos)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryListAdapter.ViewHolder =
        ViewHolder(
            SelectGalleryPhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun interface OnItemClickListener {
        fun onItemClick(selectedPhotos: ArrayList<PhotoGallery>)
    }
}

