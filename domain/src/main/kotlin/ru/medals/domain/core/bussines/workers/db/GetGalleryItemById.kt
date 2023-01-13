package ru.medals.domain.core.bussines.workers.db

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.getGalleryItemById(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		findGalleryItem = checkRepositoryData {
			galleryRepository.getById(id = galleryItem.id)
		} ?: return@handle
	}
}