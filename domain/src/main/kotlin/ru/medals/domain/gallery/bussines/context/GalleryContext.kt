package ru.medals.domain.gallery.bussines.context

import mu.KLogger
import mu.KotlinLogging
import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.gallery.model.Folder
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.domain.gallery.repository.FolderRepository

data class GalleryContext(
	var gallery: List<GalleryItem> = emptyList(),
	var isImageUpdate: Boolean = false,

	var folder: Folder = Folder(),
	var folders: List<Folder> = emptyList(),

	val log: KLogger = KotlinLogging.logger {}

) : BaseContext(command = GalleryCommand.NONE) {
	val folderRepository: FolderRepository by inject(FolderRepository::class.java)
	val awardRepository: AwardRepository by inject(AwardRepository::class.java)
}

enum class GalleryCommand : IBaseCommand {
	NONE,
	ADD,
	DELETE,
	UPDATE,
	GET_BY_FOLDER,
	CREATE_FOLDER,
	GET_FOLDERS
}