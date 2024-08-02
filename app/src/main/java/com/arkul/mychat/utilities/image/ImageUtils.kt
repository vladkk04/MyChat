package com.arkul.mychat.utilities.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arkul.mychat.R
import com.arkul.mychat.utilities.extensions.toDp
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class ImageUtils(
    private val context: Context,
) {
    fun createCircleDrawable(
        bitmap: Bitmap,
        width: Int = 24,
        height: Int = 24,
        cornerRadius: Float = 48f,
        backgroundColor: Int = R.color.color_primary_night
    ): Drawable {
        val widthDp = width.toDp(context)
        val heightDp = height.toDp(context)

        val shapeableImageView = ShapeableImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(widthDp, heightDp)
            scaleType = ImageView.ScaleType.CENTER_CROP
            setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            setImageBitmap(bitmap)
            shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, cornerRadius)
                .build()
        }

        shapeableImageView.measure(
            View.MeasureSpec.makeMeasureSpec(widthDp, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(heightDp, View.MeasureSpec.EXACTLY)
        )
        shapeableImageView.layout(0, 0, widthDp, heightDp)

        val bitmap = Bitmap.createBitmap(
            shapeableImageView.width,
            shapeableImageView.height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        shapeableImageView.draw(canvas)

        return BitmapDrawable(context.resources, bitmap)
    }
}
/*private fun rotateBitmap(source: Bitmap?, angle: Float): Bitmap? {
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
}*/
