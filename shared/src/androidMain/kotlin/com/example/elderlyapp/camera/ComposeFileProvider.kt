package com.example.elderlyapp.camera

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import kotlin.time.Clock

object ComposeFileProvider {

    fun getImageUri(context: Context): Uri? = runCatching {
        val tempFile = File.createTempFile(
            "picture_${Clock.System.now()}",
            ".png",
            context.cacheDir
        ).apply {
            createNewFile()
        }

        val authority = "${context.applicationContext.packageName}.provider"

        FileProvider.getUriForFile(
            context,
            authority,
            tempFile
        )
    }.getOrNull()
}