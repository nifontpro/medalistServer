package ru.medals.domain.gallery.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.validateGalleryFolderId(title: String) = worker {
	this.title = title
	on { galleryItem.folderId.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "name",
				violationCode = "empty",
				description = "folderId не должно быть пустым"
			)
		)
	}
}
