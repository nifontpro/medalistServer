package ru.medals.domain.gallery.bussines.workers.db

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.updateGalleryItemImage(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING && fileData.size > 0 }
	handle {
		checkRepositoryData {
			galleryRepository.updateImage(item = findGalleryItem, fileData = fileData)
		}
	}

}