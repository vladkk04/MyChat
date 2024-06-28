package com.arkul.mychat.utilities.dialogs

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkul.mychat.R
import com.arkul.mychat.data.models.PhotoGallery
import com.arkul.mychat.databinding.SuggestionGalleryAlertDialogBinding
import com.arkul.mychat.ui.adapters.recyclerViews.GalleryListAdapter
import com.arkul.mychat.utilities.constants.Constants
import com.arkul.mychat.utilities.openAppSettings
import com.google.android.material.dialog.MaterialAlertDialogBuilder


private var isShowingDialog = false

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


fun Fragment.createSuggestionGalleryDialog(
    isMediaLimitAccess: Boolean = false,
    onAddClickListener: ((ArrayList<PhotoGallery>) -> Unit)
): AlertDialog? {
    if (isShowingDialog) { return null }

    val binding = SuggestionGalleryAlertDialogBinding.inflate(layoutInflater)

    binding.mediaLimitAccess.root.visibility =
        if (isMediaLimitAccess) View.VISIBLE else View.INVISIBLE

    binding.mediaLimitAccess.buttonMediaLimitAccess.setOnClickListener {
        openAppSettings()
    }

    val recyclerView = binding.recycleViewGalleryAlertDialog

    val galleryAdapter = GalleryListAdapter()

    galleryAdapter.setList(getPhotosFromGallery(requireContext()))

    with(recyclerView) {
        adapter = galleryAdapter
        layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        setHasFixedSize(true)
    }

    val dialog = MaterialAlertDialogBuilder(requireContext())
        .setTitle("Select Photos")
        .setPositiveButton("Add") { _, _ ->
            onAddClickListener.invoke(galleryAdapter.selectedPhotos)
        }
        .setNeutralButton("Cancel") { view, _ -> view.dismiss() }
        .setView(binding.root)
        .create()

    return dialog.apply {
        this.setOnShowListener {
            getButton(AlertDialog.BUTTON_POSITIVE).apply {
                galleryAdapter.setOnItemClickListener { list ->
                    text =
                        if (list.size > 0) "Add ${list.size} / (${Constants.MAX_SELECTABLE_PHOTOS_FROM_GALLERY_SUGGESTION_INT})"
                        else "Add"
                }
            }
        }
    }
}
