package ru.medals.domain.gallery.bussines.workers.folder.db

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<GalleryContext>.checkParentFolderExist(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		val isExist = checkRepositoryData {
			folderRepository.checkExistById(folderId = folder.parentId)
		} ?: return@handle

		if (!isExist) {
			fail(
				errorValidation(
					field = "parent",
					violationCode = "not exist",
					description = "Родительская папка не найдена"
				)
			)
		}
	}

}