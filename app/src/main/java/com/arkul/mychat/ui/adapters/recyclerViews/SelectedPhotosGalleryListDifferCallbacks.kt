package com.arkul.mychat.ui.adapters.recyclerViews

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.arkul.mychat.data.models.PhotoGallery
import com.arkul.mychat.data.models.Suggestion

class SelectedPhotosGalleryListDifferCallbacks: DiffUtil.ItemCallback<PhotoGallery>() {
    override fun areItemsTheSame(oldItem: PhotoGallery, newItem: PhotoGallery): Boolean {
        Log.d("debug", "areItemsTheSame: $oldItem and $newItem")
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: PhotoGallery, newItem: PhotoGallery): Boolean {
        Log.d("debug", "gg: $oldItem and $newItem")

        return oldItem == newItem
    }

}