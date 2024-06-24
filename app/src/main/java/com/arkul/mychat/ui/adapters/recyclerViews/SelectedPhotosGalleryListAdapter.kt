package com.arkul.mychat.ui.adapters.recyclerViews

import android.view.LayoutInflater
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

    private var onButtonChange: OnItemButtonChangeSizeAddSelectPhotoView? = null

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
        asyncListDiffer.submitList(listOf(null) + list) {
            onButtonChange?.onChange()
        }
    }

    inner class ViewHolderSelectedPhotoGallery(val binding: SelectedGalleryPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
                asyncListDiffer.submitList(newList) {
                    onButtonChange?.onChange()
                }
            }
        }
    }


    inner class ViewHolderButtonSelectPhotoGallery(private val binding: ButtonSelectPhotoGalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            if (onButtonChange == null) {
                onButtonChange = object : OnItemButtonChangeSizeAddSelectPhotoView {
                    override fun onChange() {
                        adjustLayoutParams()
                    }
                }
            }
        }

        fun bind() {
            binding.imageViewButtonSelectPhotoFromGallery.setOnClickListener {
                onItemAddSelectPhotoView?.onClick()
            }
            adjustLayoutParams()
        }

        private fun adjustLayoutParams() {
            val params = itemView.layoutParams
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.width =
                if (asyncListDiffer.currentList.size != 1) 100.toDp(binding.root.context)
                else ViewGroup.LayoutParams.MATCH_PARENT
            itemView.layoutParams = params
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

    internal interface OnItemButtonChangeSizeAddSelectPhotoView {
        fun onChange()
    }
}
