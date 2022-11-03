package ru.medals.data.core

import ru.medals.domain.core.util.Constants
import java.io.File

fun deleteImageFile(
    imageUrl: String?
) {
    imageUrl?.let { url ->
        File(Constants.LOCAL_FOLDER + url).delete()
    }
}