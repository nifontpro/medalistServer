package ru.medals.domain.gallery.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.trimFieldGalleryItem(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		galleryItem = galleryItem.copy(
			name = galleryItem.name.trim(),
			description = galleryItem.description?.trim(),
		)
	}
}
