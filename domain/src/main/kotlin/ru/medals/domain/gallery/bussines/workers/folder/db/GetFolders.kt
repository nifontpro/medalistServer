package ru.medals.domain.gallery.bussines.workers.folder.db

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.getFolders(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		folders = checkRepositoryData {
			folderRepository.getFolders(parentId = folder.parentId)
		} ?: return@handle
	}

}