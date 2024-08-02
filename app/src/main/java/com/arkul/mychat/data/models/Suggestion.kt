package com.arkul.mychat.data.models

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.arkul.mychat.ui.adapters.recyclerViews.BaseModel


data class Suggestion(
    val topic: String,
    val description: String = "",
    val avatarAuthor: Bitmap? = null,
    val isAnonymous: Boolean = false,
    val photos: List<Uri> = emptyList(),
    val videos: List<Uri> = emptyList(),
    val thumbDownCount: Int = 0,
    val thumbUpCount: Int = 0,
    val isThumbedDown: Boolean = false,
    val isThumbedUp: Boolean = false
) : BaseModel(), Parcelable {
    constructor(parcel: Parcel) : this(
        topic = parcel.readString() ?: "",
        description = parcel.readString() ?: "",
        avatarAuthor = parcel.readParcelable(Bitmap::class.java.classLoader),
        isAnonymous = parcel.readInt() != 0,
        photos = parcel.createTypedArrayList(Uri.CREATOR)?.toList() ?: emptyList(),
        videos = parcel.createTypedArrayList(Uri.CREATOR)?.toList() ?: emptyList(),
        thumbDownCount = parcel.readInt(),
        thumbUpCount = parcel.readInt(),
        isThumbedDown = parcel.readInt() != 0,
        isThumbedUp = parcel.readInt() != 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(topic)
        parcel.writeString(description)
        parcel.writeParcelable(avatarAuthor, flags)
        parcel.writeInt(if (isAnonymous) 1 else 0)
        parcel.writeTypedList(photos)
        parcel.writeTypedList(videos)
        parcel.writeInt(thumbDownCount)
        parcel.writeInt(thumbUpCount)
        parcel.writeInt(if (isThumbedDown) 1 else 0)
        parcel.writeInt(if (isThumbedUp) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Suggestion> {
        override fun createFromParcel(parcel: Parcel): Suggestion {
            return Suggestion(parcel)
        }

        override fun newArray(size: Int): Array<Suggestion?> {
            return arrayOfNulls(size)
        }
    }
}