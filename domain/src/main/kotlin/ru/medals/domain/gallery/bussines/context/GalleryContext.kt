package ru.medals.domain.gallery.bussines.context

import mu.KLogger
import mu.KotlinLogging
import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.gallery.model.Gallery
import ru.medals.domain.gallery.repository.GalleryRepository

data class GalleryContext(
	val gallery: Gallery = Gallery(),

	val log: KLogger = KotlinLogging.logger {}

) : BaseContext(command = GalleryCommand.NONE) {
	val galleryRepository: GalleryRepository by inject(GalleryRepository::class.java)
}

enum class GalleryCommand : IBaseCommand {
	NONE,
	ADD
}