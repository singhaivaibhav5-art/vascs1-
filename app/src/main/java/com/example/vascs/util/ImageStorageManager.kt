package com.example.vascs.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ImageStorageManager {

    private const val FOLDER_NAME = "product_images"
    private const val MEDIA_LIBRARY_FOLDER = "media_library"

    data class MediaImportResult(
        val file: File,
        val uriString: String,
        val width: Int,
        val height: Int
    )

    fun getMediaLibraryDir(context: Context, source: String = "GALLERY"): File {
        val baseDir = File(context.filesDir, MEDIA_LIBRARY_FOLDER)
        val sourceDir = File(baseDir, source.uppercase())
        if (!sourceDir.exists()) {
            sourceDir.mkdirs()
        }
        return sourceDir
    }

    fun getImageDimensions(file: File): Pair<Int, Int> {
        return try {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeFile(file.absolutePath, options)
            Pair(options.outWidth, options.outHeight)
        } catch (e: Exception) {
            Pair(0, 0)
        }
    }

    fun copyUriToMediaLibrary(
        context: Context,
        sourceUri: Uri,
        imageSource: String = "GALLERY"
    ): MediaImportResult? {
        return try {
            val contentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(sourceUri)
            if (inputStream == null) return null

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(Date())
            val fileName = "media_${imageSource.lowercase()}_$timeStamp.jpg"
            val destFile = File(getMediaLibraryDir(context, imageSource), fileName)

            val outputStream = FileOutputStream(destFile)
            val buffer = ByteArray(8192)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()

            val dims = getImageDimensions(destFile)

            MediaImportResult(
                file = destFile,
                uriString = Uri.fromFile(destFile).toString(),
                width = dims.first,
                height = dims.second
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun createMediaLibraryCameraFile(context: Context): Pair<File, Uri>? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(Date())
            val fileName = "media_cam_$timeStamp.jpg"
            val file = File(getMediaLibraryDir(context, "CAMERA"), fileName)
            val authority = "${context.packageName}.fileprovider"
            val contentUri = FileProvider.getUriForFile(context, authority, file)
            Pair(file, contentUri)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getStorageDir(context: Context): File {
        val dir = File(context.filesDir, FOLDER_NAME)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun copyUriToAppStorage(context: Context, sourceUri: Uri, productId: String): Pair<File, String>? {
        return try {
            val contentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(sourceUri)
            if (inputStream == null) return null

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(Date())
            val fileName = "saree_${productId}_$timeStamp.jpg"
            val destFile = File(getStorageDir(context), fileName)

            val outputStream = FileOutputStream(destFile)
            val buffer = ByteArray(8192)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()

            // Downsample if file is extremely large to avoid OOM
            downsampleFileIfNeeded(destFile)

            Pair(destFile, Uri.fromFile(destFile).toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun saveDownloadedImage(context: Context, bytes: ByteArray, productId: String): String? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(Date())
            val fileName = "ai_catalogue_${productId}_$timeStamp.png"
            val destFile = File(getStorageDir(context), fileName)

            val outputStream = FileOutputStream(destFile)
            outputStream.write(bytes)
            outputStream.flush()
            outputStream.close()

            Uri.fromFile(destFile).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun encodeUriToBase64(context: Context, imageUriString: String): String? {
        return try {
            val uri = Uri.parse(imageUriString)
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val bytes = inputStream.readBytes()
            inputStream.close()
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun createCameraImageFile(context: Context, productId: String): Pair<File, Uri>? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(Date())
            val fileName = "saree_${productId}_CAM_$timeStamp.jpg"
            val file = File(getStorageDir(context), fileName)
            val authority = "${context.packageName}.fileprovider"
            val contentUri = FileProvider.getUriForFile(context, authority, file)
            Pair(file, contentUri)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getShareableUri(context: Context, imageUriString: String): Uri? {
        return try {
            val uri = Uri.parse(imageUriString)
            if (uri.scheme == "file") {
                val file = File(uri.path ?: return null)
                if (file.exists()) {
                    FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                } else null
            } else {
                uri
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun shareImage(context: Context, imageUriString: String, title: String = "Share Saree Image") {
        val shareableUri = getShareableUri(context, imageUriString) ?: return
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, shareableUri)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, title))
    }

    private fun downsampleFileIfNeeded(file: File, maxDimension: Int = 1920) {
        try {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeFile(file.absolutePath, options)
            val origWidth = options.outWidth
            val origHeight = options.outHeight

            if (origWidth > maxDimension || origHeight > maxDimension) {
                var sampleSize = 1
                while ((origWidth / sampleSize) > maxDimension || (origHeight / sampleSize) > maxDimension) {
                    sampleSize *= 2
                }

                val decodeOptions = BitmapFactory.Options().apply {
                    inSampleSize = sampleSize
                }
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, decodeOptions) ?: return
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 88, out)
                out.flush()
                out.close()
                bitmap.recycle()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
