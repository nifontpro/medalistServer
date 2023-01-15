package ru.medals.domain.gallery.bussines.workers.folder

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.trimFieldFolderItem(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		folder = folder.copy(
			name = folder.name.trim(),
			description = folder.description?.trim(),
		)
	}
}
