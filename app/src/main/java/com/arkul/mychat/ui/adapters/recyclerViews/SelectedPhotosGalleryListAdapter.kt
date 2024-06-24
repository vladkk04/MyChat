package com.arkul.mychat.ui.adapters.recyclerViews

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.data.models.PhotoGallery
import com.arkul.mychat.databinding.ButtonSelectPhotoGalleryItemBinding
import com.arkul.mychat.databinding.SelectedGalleryPhotoItemBinding
import com.arkul.mychat.utilities.extensions.toDp
import com.bumptech.glide.Glide


class SelectedPhotosGalleryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val asyncListDiffer = AsyncListDiffer(this, SelectedPhotosGalleryListDifferCallbacks())
    private var onItemAddSelectPhotoView: OnItemButtonAddSelectPhotoView? = null

    var addButtonSelectPhotoView = false
        set(value) {
            field = value
            asyncListDiffer.submitList(listOf(null))
        }

    companion object {
        const val TYPE_SELECTED_PHOTO_GALLERY = 0
        const val TYPE_BUTTON_SELECT_PHOTO_GALLERY = 1
    }

    fun setOnItemAddSelectPhotoView(onItemButtonAddSelectPhotoView: OnItemButtonAddSelectPhotoView) {
        this.onItemAddSelectPhotoView = onItemButtonAddSelectPhotoView
    }

    fun setList(list: List<PhotoGallery>) {
        asyncListDiffer.submitList(if (addButtonSelectPhotoView) listOf(null) + list else list)
    }

    inner class ViewHolderSelectedPhotoGallery(val binding: SelectedGalleryPhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoGallery?) {
            val imageView = binding.imageViewSelectedPhotoFromGallery
            val deleteButton = binding.checkboxDeleteSelectedPhoto

            Glide.with(binding.root.context)
                .asBitmap()
                .override(300, 300)
                .load(item?.uri)
                .into(imageView)

            deleteButton.setOnClickListener {
                val newList = asyncListDiffer.currentList.toMutableList()
                newList.removeAt(adapterPosition)
                setList(newList)
            }

            imageView.setOnClickListener {
                deleteButton.visibility =
                    if (item in asyncListDiffer.currentList) View.VISIBLE else View.INVISIBLE
            }
        }
    }


    inner class ViewHolderButtonSelectPhotoGallery(val binding: ButtonSelectPhotoGalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //val item = asyncListDiffer.currentList[adapterPosition]
        fun bind() {
            Log.d("debug", "hello")
            if (adapterPosition == 0) {
                Log.d("debug", "nigga")

                //binding.buttonSelectPhotoFromGalleryLayout.layoutParams.width = 200
            }

            binding.imageViewButtonSelectPhotoFromGallery.setOnClickListener {
                onItemAddSelectPhotoView?.onClick()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (asyncListDiffer.currentList[position] == null) {
            TYPE_BUTTON_SELECT_PHOTO_GALLERY
        } else {
            TYPE_SELECTED_PHOTO_GALLERY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BUTTON_SELECT_PHOTO_GALLERY -> {
                ViewHolderButtonSelectPhotoGallery(
                    ButtonSelectPhotoGalleryItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                ViewHolderSelectedPhotoGallery(
                    SelectedGalleryPhotoItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderButtonSelectPhotoGallery -> holder.bind()
            is ViewHolderSelectedPhotoGallery -> holder.bind(asyncListDiffer.currentList[position])
        }
    }

    fun interface OnItemButtonAddSelectPhotoView {
        fun onClick()
    }
}
