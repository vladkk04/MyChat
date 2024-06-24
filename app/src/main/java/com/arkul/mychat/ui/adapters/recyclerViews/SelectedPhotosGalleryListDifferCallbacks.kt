package com.arkul.mychat.ui.adapters.recyclerViews

import androidx.recyclerview.widget.DiffUtil
import com.arkul.mychat.data.models.PhotoGallery
import com.arkul.mychat.data.models.Suggestion

class SelectedPhotosGalleryListDifferCallbacks: DiffUtil.ItemCallback<PhotoGallery>() {
    override fun areItemsTheSame(oldItem: PhotoGallery, newItem: PhotoGallery): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: PhotoGallery, newItem: PhotoGallery): Boolean {
        return oldItem == newItem
    }

}