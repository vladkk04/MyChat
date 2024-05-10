package com.arkul.mychat.utilities.cameraX

import android.net.Uri
import android.util.Log
import android.widget.Button
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageSavedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.MirrorMode.MIRROR_MODE_ON
import androidx.camera.core.Preview
import androidx.camera.core.Preview.SurfaceProvider
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.io.File


class CameraPreview(private val fragment: Fragment) {

    private val context = fragment.requireContext()
    private val lifecycleOwner = fragment.viewLifecycleOwner
    private var surfaceProvider: SurfaceProvider? = null
    private var lensFacing = CameraSelector.LENS_FACING_FRONT
    private val imageCapture = ImageCapture.Builder().build()

    private var cameraProvider: ListenableFuture<ProcessCameraProvider> =
        ProcessCameraProvider.getInstance(context)

    fun launchCameraPreview(
        surfaceProvider: SurfaceProvider,
    ) {
        this.surfaceProvider = surfaceProvider
        cameraProvider = ProcessCameraProvider.getInstance(context).apply {
            this.addListener({
                bindPreview(lifecycleOwner, this.get(), surfaceProvider)
            }, ContextCompat.getMainExecutor(context))
        }
    }

    fun addOnFlipCameraCallback(button: Button) {
        button.setOnClickListener { flipCamera() }
    }

    fun addOnTakePictureCallback(button: Button, block: (uri: Uri?) -> Unit) {
        button.setOnClickListener {
            takePicture {
                block.invoke(it)
            }
        }
    }

    fun terminateCameraPreview() {
        if(cameraProvider.isDone) cameraProvider.get().unbindAll() else return
    }

    private fun takePicture(block: (uri: Uri?) -> Unit) {
        val metadata = ImageCapture.Metadata().apply {
            isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
        }

        val outputFileOptions =
            ImageCapture.OutputFileOptions.Builder(File(context.dataDir, "camera_image.jpg"))
                .setMetadata(metadata)
                .build()

        imageCapture.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(context),
            object : OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                    block.invoke(outputFileResults.savedUri)
                }

                override fun onError(error: ImageCaptureException) {
                    block.invoke(null)
                    Log.e("CameraPreview", "Can't take picture: ${error.message}")
                }
            })
    }

    private fun flipCamera() {
        if (lensFacing == CameraSelector.LENS_FACING_FRONT) lensFacing =
            CameraSelector.LENS_FACING_BACK
        else if (lensFacing == CameraSelector.LENS_FACING_BACK) lensFacing =
            CameraSelector.LENS_FACING_FRONT
        surfaceProvider?.let { launchCameraPreview(it) }
    }

    private fun bindPreview(
        lifecycleOwner: LifecycleOwner,
        cameraProvider: ProcessCameraProvider,
        surfaceProvider: SurfaceProvider,
    ) {
        cameraProvider.unbindAll()

        val preview: Preview = Preview.Builder().build().apply {
            this.setSurfaceProvider(surfaceProvider)
        }

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()

        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
    }
}

