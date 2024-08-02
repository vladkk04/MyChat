package com.arkul.mychat.ui.adapters.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.data.models.PhotoGallery
import com.arkul.mychat.databinding.ButtonSelectPhotoGalleryItemBinding
import com.arkul.mychat.databinding.SelectedGalleryPhotoItemBinding
import com.arkul.mychat.ui.adapters.recyclerViews.differs.SelectedPhotoGalleryListDifferCallbacks
import com.arkul.mychat.utilities.extensions.toDp
import com.bumptech.glide.Glide


class SelectedPhotosGalleryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val asyncListDiffer = AsyncListDiffer(this, SelectedPhotoGalleryListDifferCallbacks())

    private var onItemAddSelectPhotoView: OnItemButtonAddSelectPhotoView? = null
    private var onButtonChange: OnItemButtonChangeSizeAddSelectPhotoView? = null

    var addButtonSelectPhotoView = false
        set(value) {
            field = value
            asyncListDiffer.submitList(listOf(null))
        }

    private var widthItem = 100
    private var heightItem = 100

    companion object {
        const val TYPE_SELECTED_PHOTO_GALLERY = 0
        const val TYPE_BUTTON_SELECT_PHOTO_GALLERY = 1
    }

    fun setOnItemAddSelectPhotoView(onItemButtonAddSelectPhotoView: OnItemButtonAddSelectPhotoView) {
        this.onItemAddSelectPhotoView = onItemButtonAddSelectPhotoView
    }

    fun setSizeItem(width: Int, height: Int) {
        widthItem = width
        heightItem = height
    }

    fun setList(list: List<PhotoGallery>) {
        val newList = if(addButtonSelectPhotoView) listOf(null) + list else list

        asyncListDiffer.submitList(newList) {
            onButtonChange?.onChange()
        }
    }

    fun clearList() {
        asyncListDiffer.submitList(emptyList())
    }

    inner class ViewHolderSelectedPhotoGallery(val binding: SelectedGalleryPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoGallery?) {
            val imageView = binding.imageViewSelectedPhotoFromGallery
            val deleteButton = binding.checkboxDeleteSelectedPhoto

            imageView.layoutParams.width = widthItem.toDp(binding.root.context)
            imageView.layoutParams.height = heightItem.toDp(binding.root.context)

            Glide.with(binding.root.context)
                .asBitmap()
                .override(300, 300)
                .load(item?.uri)
                .into(imageView)

            deleteButton.setOnClickListener {
                val newList = asyncListDiffer.currentList.toMutableList()
                newList.remove(item)
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
            val params = itemView.layoutParams as ViewGroup.MarginLayoutParams

            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.width =
                if (asyncListDiffer.currentList.size != 1) {
                    params.setMargins(0, 0, 10.toDp(binding.root.context), 0)
                    widthItem.toDp(binding.root.context)
                }
                else {
                    params.setMargins(0, 0, 0, 0)
                    ViewGroup.LayoutParams.MATCH_PARENT
                }

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
