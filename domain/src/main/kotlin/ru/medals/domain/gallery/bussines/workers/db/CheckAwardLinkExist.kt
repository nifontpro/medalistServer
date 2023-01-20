package ru.medals.domain.gallery.bussines.workers.db

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.checkAwardLinkExist(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		val exist = checkRepositoryData {
			awardRepository.galleryItemLinkExist(itemId = findGalleryItem.id)
		} ?: return@handle

		if (exist) {
			fail(
				errorValidation(
					field = "award",
					violationCode = "link exist",
					description = "У некоторых наград установлена ссылка на данное изображение"
				)
			)
		}
	}

}