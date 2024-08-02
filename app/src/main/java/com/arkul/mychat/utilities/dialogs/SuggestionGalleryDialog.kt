package com.arkul.mychat.utilities.dialogs

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.data.models.PhotoGallery
import com.arkul.mychat.databinding.SuggestionGalleryAlertDialogBinding
import com.arkul.mychat.ui.adapters.recyclerViews.GalleryListAdapter
import com.arkul.mychat.utilities.constants.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SuggestionGalleryDialog(
    private val fragment: Fragment
) {
    private var isShowingDialog = false
    private val context = fragment.requireContext()

    private var galleryAdapter = GalleryListAdapter()
    private var onMediaLimitAccessClickListener: OnMediaLimitAccessClickListener? = null
    private var onAddPhotosClickListener: OnAddPhotosClickListener? = null

    fun setOnMediaLimitAccessClickListener(onMediaLimitAccessClickListener: OnMediaLimitAccessClickListener) {
        this.onMediaLimitAccessClickListener = onMediaLimitAccessClickListener
    }

    fun setOnAddPhotosClickListener(onAddPhotosClickListener: OnAddPhotosClickListener) {
        this.onAddPhotosClickListener = onAddPhotosClickListener
    }

    fun needToRefreshList() {
        galleryAdapter.setList(getPhotosFromGallery(context))
        galleryAdapter.notifyDataSetChanged()
    }

    private fun getPhotosFromGallery(context: Context): ArrayList<PhotoGallery> {
        val imagePathList = ArrayList<PhotoGallery>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns._ID)
        val sortOrder = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                imagePathList.add(PhotoGallery(ContentUris.withAppendedId(uri, id)))
            }
        }
        return imagePathList
    }

    fun createSuggestionGalleryDialog(
        isMediaLimitAccess: Boolean = false,
    ): AlertDialog? {
        if (isShowingDialog) return null

        val binding = SuggestionGalleryAlertDialogBinding.inflate(fragment.layoutInflater)

        binding.mediaLimitAccess.root.visibility =
            if (isMediaLimitAccess) View.VISIBLE else View.GONE

        binding.mediaLimitAccess.buttonMediaLimitAccess.setOnClickListener {
            onMediaLimitAccessClickListener?.onClick()
        }

        val dialog = MaterialAlertDialogBuilder(context)
            .setTitle("Select Photos")
            .setPositiveButton("Add") { _, _ ->
                onAddPhotosClickListener?.onClick(galleryAdapter.selectedPhotos)
            }
            .setOnDismissListener {
                isShowingDialog = false
            }
            .setNeutralButton("Cancel") { view, _ -> view.dismiss() }
            .setView(binding.root)
            .create()

        setupRecyclerView(binding)

        return dialog.apply {
            isShowingDialog = true

            this.setOnShowListener {
                getButton(AlertDialog.BUTTON_POSITIVE).apply {
                    galleryAdapter.setOnItemClickListener { list ->
                        text =
                            if (list.isNotEmpty()) "Add ${list.size} / (${Constants.MAX_SELECTABLE_PHOTOS_FROM_GALLERY_SUGGESTION_INT})"
                            else "Add"
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(binding: SuggestionGalleryAlertDialogBinding) {
        val recyclerView = binding.recycleViewGalleryAlertDialog

        galleryAdapter = GalleryListAdapter()
        galleryAdapter.setList(getPhotosFromGallery(context))

        with(recyclerView) {
            adapter = galleryAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }
    }

    fun interface OnMediaLimitAccessClickListener {
        fun onClick()
    }

    fun interface OnAddPhotosClickListener {
        fun onClick(photos: ArrayList<PhotoGallery>)
    }
}

