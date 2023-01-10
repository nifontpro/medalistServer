package ru.medals.domain.gallery.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.addGalleryItem(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		checkRepositoryData {
			galleryRepository.addItem(galleryItem = galleryItem, fileData = fileData)
		} ?: return@handle
	}

}