package ru.medals.ktor.core

import io.ktor.http.content.*
import ru.medals.domain.core.util.Constants.LOCAL_FOLDER
import ru.medals.domain.image.model.FileData
import java.io.File
import java.util.*

fun PartData.FileItem.save(): FileData? {
	val fileBytes = streamProvider().readBytes()
	val fileSize = fileBytes.size
	if (fileBytes.isEmpty()) return null
	val fileExtension = originalFileName?.takeLastWhile { it != '.' }

//	val fileName = originalFileName.toString().split('.')[0] + "#" +
//					UUID.randomUUID().toString() + "." + fileExtension

	val fileName = UUID.randomUUID().toString() + "." + fileExtension

	return try {
		val folder = File(LOCAL_FOLDER)
		folder.mkdirs()
		val url = "$LOCAL_FOLDER/$fileName"
		val file = File(url)
		file.writeBytes(fileBytes)
		FileData(
			url = url,
			filename = fileName,
			size = fileSize
		)
	} catch (e: Exception) {
		null
	}
}