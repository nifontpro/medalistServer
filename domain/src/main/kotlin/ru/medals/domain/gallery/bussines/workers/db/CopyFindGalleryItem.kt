package ru.medals.domain.gallery.bussines.workers.db

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.copyFindGalleryItemForResult(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		galleryItem = findGalleryItem
	}

}