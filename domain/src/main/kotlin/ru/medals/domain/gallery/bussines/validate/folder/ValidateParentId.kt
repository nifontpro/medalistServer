package ru.medals.domain.gallery.bussines.validate.folder

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.validateParentId(title: String) = worker {
	this.title = title
	on { folder.parentId.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "parentId",
				violationCode = "empty",
				description = "parentId не должен быть пустым"
			)
		)
	}
}
