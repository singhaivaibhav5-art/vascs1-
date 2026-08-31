package com.example.vascs.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class ResizePreset(
    val title: String,
    val width: Int,
    val height: Int,
    val ratioLabel: String
) {
    SQUARE("1:1 Catalogue Square", 1080, 1080, "1:1"),
    PORTRAIT("4:5 Instagram Portrait", 1080, 1350, "4:5"),
    STORY("9:16 Full Screen / Reel", 1080, 1920, "9:16"),
    LANDSCAPE("16:9 Banner", 1920, 1080, "16:9"),
    THUMBNAIL("800x800 Web Standard", 800, 800, "1:1")
}

enum class FitMode {
    CENTER_CROP,
    FIT_CENTER_PADDING
}

object ImageResizeEngine {

    data class ResizeResult(
        val destFile: File,
        val uriString: String,
        val width: Int,
        val height: Int
    )

    fun loadBitmapFromUri(context: Context, uriString: String): Bitmap? {
        return try {
            val uri = Uri.parse(uriString)
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                bitmap
            } else if (uri.path != null) {
                BitmapFactory.decodeFile(uri.path)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun resizeBitmap(
        source: Bitmap,
        targetWidth: Int,
        targetHeight: Int,
        fitMode: FitMode = FitMode.CENTER_CROP,
        backgroundColor: Int = Color.WHITE
    ): Bitmap {
        val output = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        canvas.drawColor(backgroundColor)

        val srcWidth = source.width.toFloat()
        val srcHeight = source.height.toFloat()

        when (fitMode) {
            FitMode.CENTER_CROP -> {
                val scale = Math.max(targetWidth / srcWidth, targetHeight / srcHeight)
                val scaledW = srcWidth * scale
                val scaledH = srcHeight * scale
                val dx = (targetWidth - scaledW) / 2f
                val dy = (targetHeight - scaledH) / 2f

                val matrix = Matrix().apply {
                    postScale(scale, scale)
                    postTranslate(dx, dy)
                }
                val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
                canvas.drawBitmap(source, matrix, paint)
            }
            FitMode.FIT_CENTER_PADDING -> {
                val scale = Math.min(targetWidth / srcWidth, targetHeight / srcHeight)
                val scaledW = srcWidth * scale
                val scaledH = srcHeight * scale
                val dx = (targetWidth - scaledW) / 2f
                val dy = (targetHeight - scaledH) / 2f

                val matrix = Matrix().apply {
                    postScale(scale, scale)
                    postTranslate(dx, dy)
                }
                val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
                canvas.drawBitmap(source, matrix, paint)
            }
        }
        return output
    }

    fun resizeAndSave(
        context: Context,
        sourceUriString: String,
        targetWidth: Int,
        targetHeight: Int,
        fitMode: FitMode = FitMode.CENTER_CROP
    ): ResizeResult? {
        val srcBitmap = loadBitmapFromUri(context, sourceUriString) ?: return null
        val resizedBitmap = resizeBitmap(srcBitmap, targetWidth, targetHeight, fitMode)

        return try {
            val resizedDir = ImageStorageManager.getMediaLibraryDir(context, "RESIZED")
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(Date())
            val fileName = "resized_${targetWidth}x${targetHeight}_$timeStamp.jpg"
            val destFile = File(resizedDir, fileName)

            val outStream = FileOutputStream(destFile)
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 92, outStream)
            outStream.flush()
            outStream.close()

            if (srcBitmap != resizedBitmap && !srcBitmap.isRecycled) {
                srcBitmap.recycle()
            }
            if (!resizedBitmap.isRecycled) {
                resizedBitmap.recycle()
            }

            val destUriString = Uri.fromFile(destFile).toString()
            ResizeResult(
                destFile = destFile,
                uriString = destUriString,
                width = targetWidth,
                height = targetHeight
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
