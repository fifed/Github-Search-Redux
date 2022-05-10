package com.fifed.tools.utils.impl

import android.content.Context
import com.fifed.tools.utils.FileUtils
import java.io.File

internal class FileUtilsImpl(private val context: Context) : FileUtils {

    override fun removeFile(file: File) {
        file.delete()
        if (file.exists()) {
            file.canonicalFile.delete()
            if (file.exists()) {
                context.deleteFile(file.name)
            }
        }
    }
}