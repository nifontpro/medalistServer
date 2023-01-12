package ru.medals.domain.gallery.bussines.workers.db

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.updateGalleryItem(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {

		val updateDate = (checkRepositoryData {
			galleryRepository.update(item = galleryItem)
		} ?: return@handle).updateDate

		galleryItem = galleryItem.copy(
			imageUrl = findGalleryItem.imageUrl,
			imageKey = findGalleryItem.imageKey,
			countLink = findGalleryItem.countLink,
			createDate = findGalleryItem.createDate,
			updateDate = updateDate
		)
	}

}