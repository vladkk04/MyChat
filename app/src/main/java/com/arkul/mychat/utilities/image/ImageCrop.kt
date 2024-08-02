package com.arkul.mychat.utilities.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import com.arkul.mychat.R
import com.yalantis.ucrop.UCrop
import java.io.File

typealias CropResultCallback = (Bitmap) -> Unit

class ImageCropActivityResultContract(
    fragment: Fragment,
    private var uCropOptions: UCrop.Options = UCrop.Options()
) {
    private val contextFragment = fragment.requireContext()
    private var cropImageResultCallback: CropResultCallback? = null
    private var fileName = "image_cropped.jpg"

    private val cropActivityResultContract = object : ActivityResultContract<Uri, Uri?>() {
        override fun createIntent(context: Context, input: Uri): Intent {
            val option = uCropOptions.apply {
                this.setToolbarColor(
                    contextFragment.resources.getColor(
                        R.color.color_primary_container_night, null
                    )
                )
                this.setStatusBarColor(
                    contextFragment.resources.getColor(
                        R.color.color_primary_container_night, null
                    )
                )
                this.setToolbarWidgetColor(
                    contextFragment.resources.getColor(
                        R.color.color_primary_night, null
                    )
                )
                this.setRootViewBackgroundColor(
                    contextFragment.resources.getColor(
                        R.color.color_background_night, null
                    )
                )
                this.setActiveControlsWidgetColor(
                    contextFragment.resources.getColor(
                        R.color.color_on_primary_night, null
                    )
                )
            }

            val existingFile = File(contextFragment.dataDir, fileName)

            return UCrop.of(input, Uri.fromFile(existingFile)).withOptions(option).getIntent(contextFragment)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return intent?.let { UCrop.getOutput(it) }
        }
    }

    private var cropImageLauncher = fragment.registerForActivityResult(cropActivityResultContract) { uri ->
        uri?.let {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contextFragment.contentResolver, it))
            } else {
                MediaStore.Images.Media.getBitmap(contextFragment.contentResolver, it)
            }
            cropImageResultCallback?.invoke(bitmap)
        }
    }

    fun setUCropOptions(options: UCrop.Options) {
        uCropOptions = options
    }

    fun startCropActivity(input: Uri?, nameOfFile: String, callback: CropResultCallback) {
        fileName = nameOfFile
        cropImageResultCallback = callback
        cropImageLauncher.launch(input)
    }
}

