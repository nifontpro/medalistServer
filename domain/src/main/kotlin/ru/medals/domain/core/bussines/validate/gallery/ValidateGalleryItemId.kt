package ru.medals.domain.core.bussines.validate.gallery

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.validateGalleryItemId(title: String) = worker {
	this.title = title
	on { galleryItem.id.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "galleryItemId",
				violationCode = "empty",
				description = "id объекта галереи не должен быть пустым"
			)
		)
	}
}
