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

    fun getFileFromUri(context: Context, uri: Uri?): File? {
        return runCatching {
            uri ?: return null
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null

            val tempFile = File.createTempFile("temp", ".jpg", context.cacheDir)
            FileOutputStream(tempFile).use { output ->
                inputStream.copyTo(output)
            }

            if (tempFile.length() > MAX_FILE_SIZE_MB) {
                compressFile(tempFile, context)
            } else {
                tempFile
            }
        }.getOrNull()
    }

    private fun compressFile(file: File, context: Context): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        val outputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

        val compressedFile = File.createTempFile("compressed", ".jpg", context.cacheDir)
        FileOutputStream(compressedFile).use { output ->
            output.write(outputStream.toByteArray())
        }

        return compressedFile
    }
}
