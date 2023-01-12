package ru.medals.domain.gallery.bussines.context

import mu.KLogger
import mu.KotlinLogging
import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.domain.gallery.repository.GalleryRepository

data class GalleryContext(
	var galleryItem: GalleryItem = GalleryItem(),
	var findGalleryItem: GalleryItem = GalleryItem(),
	var gallery: List<GalleryItem> = emptyList(),
	var isImageUpdate: Boolean = false,

	val log: KLogger = KotlinLogging.logger {}

) : BaseContext(command = GalleryCommand.NONE) {
	val galleryRepository: GalleryRepository by inject(GalleryRepository::class.java)
}

enum class GalleryCommand : IBaseCommand {
	NONE,
	ADD,
	DELETE,
	UPDATE,
	GET_BY_FOLDER
}