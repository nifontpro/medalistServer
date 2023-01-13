package ru.medals.domain.gallery.bussines.context

import mu.KLogger
import mu.KotlinLogging
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.gallery.model.GalleryItem

data class GalleryContext(
	var gallery: List<GalleryItem> = emptyList(),
	var isImageUpdate: Boolean = false,

	val log: KLogger = KotlinLogging.logger {}

) : BaseContext(command = GalleryCommand.NONE)

enum class GalleryCommand : IBaseCommand {
	NONE,
	ADD,
	DELETE,
	UPDATE,
	GET_BY_FOLDER
}