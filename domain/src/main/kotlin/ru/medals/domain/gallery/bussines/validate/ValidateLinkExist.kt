package ru.medals.domain.gallery.bussines.validate

import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.validateLinkExist(title: String) = worker {
	this.title = title
	on { findGalleryItem.countLink > 0 }
	handle {
		fail(
			errorDb(
				repository = "gallery",
				violationCode = "link exist",
				description = "Невоможно удалить объект галереи при наличии ссылок на него"
			)
		)
	}
}
