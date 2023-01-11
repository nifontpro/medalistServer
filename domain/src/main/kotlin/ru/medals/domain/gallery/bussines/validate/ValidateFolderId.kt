package ru.medals.domain.gallery.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.validateFolderId(title: String) = worker {
	this.title = title
	on { galleryItem.folderId != "medal" }
	handle {
		fail(
			errorValidation(
				field = "folderId",
				violationCode = "not found",
				description = "В данной версии программы folderId может быть только 'medal'"
			)
		)
	}
}
