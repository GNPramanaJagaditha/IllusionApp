package com.example.illusionapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    private const val MAX_FILE_SIZE_MB = 1 * 1024 * 1024 // 1 MB

    /**
     * Converts a Uri to a File and compresses it if the size exceeds the limit.
     * @param context Context for accessing file resources.
     * @param uri Uri of the selected image.
     * @return A processed File or null if the Uri is invalid.
     */
    fun getFileFromUri(context: Context, uri: Uri?): File? {
        return runCatching {
            uri ?: return null
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null

            // Create a temporary file in the cache directory
            val tempFile = File.createTempFile("temp", ".jpg", context.cacheDir)
            FileOutputStream(tempFile).use { output ->
                inputStream.copyTo(output)
            }

            // Compress file if it exceeds the maximum size
            if (tempFile.length() > MAX_FILE_SIZE_MB) {
                compressFile(tempFile, context)
            } else {
                tempFile
            }
        }.getOrNull() // Returns null if any exception occurs
    }

    /**
     * Compresses a file by reducing its quality and returns the compressed File.
     * @param file The original File to be compressed.
     * @param context Context for accessing file resources.
     * @return A compressed File.
     */
    private fun compressFile(file: File, context: Context): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        val outputStream = ByteArrayOutputStream()

        // Compress the image to 80% quality
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

        // Create a new temporary file for the compressed image
        val compressedFile = File.createTempFile("compressed", ".jpg", context.cacheDir)
        FileOutputStream(compressedFile).use { output ->
            output.write(outputStream.toByteArray())
        }

        return compressedFile
    }
}
