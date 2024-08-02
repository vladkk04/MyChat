package com.arkul.mychat.data.models

import android.net.Uri
import com.arkul.mychat.ui.adapters.recyclerViews.BaseModel
import com.arkul.mychat.utilities.date.getCurrentTime_AM_PM

data class Message(
    val text: String = "",
    val photos: List<Uri> = emptyList(),
    val time: String = getCurrentTime_AM_PM,
    val typeOfMessage: TypeOfMessage? = determineType(text, photos)
): BaseModel() {
    companion object {
        private fun determineType(text: String, photos: List<Uri>): TypeOfMessage? {
            return when {
                text.isNotEmpty() && photos.isNotEmpty() -> TypeOfMessage.PHOTO_TEXT
                text.isNotEmpty() && photos.isEmpty() -> TypeOfMessage.TEXT
                text.isEmpty() && photos.isNotEmpty() -> TypeOfMessage.PHOTO
                else -> null
            }
        }
    }

    enum class TypeOfMessage {
        PHOTO_TEXT,
        PHOTO,
        TEXT
    }
}
