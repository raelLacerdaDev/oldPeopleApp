package com.example.elderlyapp.data

import com.example.elderlyapp.gallery.SharedImage
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import platform.Foundation.NSDate
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.writeToFile
import platform.UIKit.UIImageJPEGRepresentation

class IosImageStorage : ImageStorage{
    @OptIn(ExperimentalForeignApi::class)
    override suspend fun saveImage(sharedImage: SharedImage): String? {
        return withContext(Dispatchers.IO) {
            runCatching {
                val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )?.path ?: return@runCatching null

                val fileName = "memory_${NSDate().timeIntervalSince1970}.jpg"
                val filePath = "$documentDirectory/$fileName"

                val imagedata = UIImageJPEGRepresentation(sharedImage.uiImage, 0.9)
                imagedata?.writeToFile(filePath,true)
                filePath
            }.getOrNull()
        }
    }
}