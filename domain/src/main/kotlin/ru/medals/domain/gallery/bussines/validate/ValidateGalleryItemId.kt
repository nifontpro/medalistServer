package ru.medals.domain.gallery.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.validateGalleryItemId(title: String) = worker {
	this.title = title
	on { galleryItem.id.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "id",
				violationCode = "empty",
				description = "id не должен быть пустым"
			)
		)
	}
}
