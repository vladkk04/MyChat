package com.arkul.mychat.ui.adapters.recyclerViews.differs

import androidx.recyclerview.widget.DiffUtil
import com.arkul.mychat.data.models.PhotoGallery

class SelectedPhotoGalleryListDifferCallbacks: DiffUtil.ItemCallback<PhotoGallery>() {
    override fun areItemsTheSame(oldItem: PhotoGallery, newItem: PhotoGallery): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: PhotoGallery, newItem: PhotoGallery): Boolean {
        return oldItem == newItem
    }

}