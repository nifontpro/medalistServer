package ru.medals.domain.gallery.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.gallery.model.Folder

interface FolderRepository {
	suspend fun create(folder: Folder): RepositoryData<Folder>
	suspend fun checkExistById(folderId: String): RepositoryData<Boolean>
	suspend fun getFolders(parentId: String): RepositoryData<List<Folder>>
}