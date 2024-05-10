package com.arkul.mychat.utilities.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

private fun rotateBitmap(source: Bitmap?, angle: Float): Bitmap? {
    if (source == null) return null
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
}

suspend fun Fragment.compressImage(uri: Uri): Bitmap? {
    var bitmap: Bitmap? = null
    withContext(Dispatchers.IO) {
        requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
            val byteArray = inputStream.readBytes()

            val exif = ExifInterface(ByteArrayInputStream(byteArray))
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            //TODO Implement sample size logic
            val decodedBitmapOption = BitmapFactory.Options().apply {
                inJustDecodeBounds = false
                inSampleSize = 2
            }

            val newBitmap = BitmapFactory.decodeByteArray(
                byteArray,
                0,
                byteArray.size,
                decodedBitmapOption
            )

            val rotatedBitmap = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(newBitmap, 90f)
                else -> newBitmap
            }

            rotatedBitmap?.apply {
                compress(Bitmap.CompressFormat.PNG, 70, ByteArrayOutputStream())
            }

            bitmap = rotatedBitmap
        }
    }
    return bitmap
}
