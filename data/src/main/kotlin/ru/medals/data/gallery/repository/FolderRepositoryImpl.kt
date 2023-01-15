package ru.medals.data.gallery.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.push
import ru.medals.data.gallery.model.FolderCol
import ru.medals.data.gallery.model.folderColBuild
import ru.medals.data.gallery.repository.FolderRepoErrors.Companion.errorFolderCreate
import ru.medals.data.gallery.repository.FolderRepoErrors.Companion.errorFolderCreateByParent
import ru.medals.data.gallery.repository.FolderRepoErrors.Companion.errorGetFolder
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.util.separator
import ru.medals.domain.gallery.model.Folder
import ru.medals.domain.gallery.repository.FolderRepository

class FolderRepositoryImpl(
	db: CoroutineDatabase,
) : FolderRepository {

	private val folders = db.getCollection<FolderCol>()

	init {
		/**
		 * Если нет корневой папки, то создаем ее
		 */
		CoroutineScope(Dispatchers.IO).launch {
			val rootExist = checkExistById(folderId = ROOT)
			if (rootExist.success && rootExist.data == false) {
				separator()
				println("Create root folder")
				createRoot()
			}
		}
	}

	/**
	 * Создание корневой папки
	 */
	private suspend fun createRoot() {
		val rootFolderCol = FolderCol(id = ROOT, name = ROOT, createDate = System.currentTimeMillis())
		try {
			folders.insertOne(rootFolderCol)
		} catch (e: Exception) {
			println(e.message)
		}
	}

	/**
	 * Создаем папку для хранения объектов
	 */
	override suspend fun create(folder: Folder): RepositoryData<Folder> {
		val folderCol = folderColBuild { folderBuild = folder }

		try {
			folders.insertOne(folderCol)
		} catch (e: Exception) {
			return errorFolderCreate()
		}

		return try {
			folders.updateOneById(
				id = folder.parentId,
				update = push(FolderCol::childrenIds, folderCol.id)
			)
			RepositoryData.success(data = folderCol.toFolder())
		} catch (e: Exception) {
			folders.deleteOneById(id = folderCol.id)
			errorFolderCreateByParent()
		}
	}

	override suspend fun checkExistById(folderId: String): RepositoryData<Boolean> = try {
		val isExist = folders.countDocuments(FolderCol::id eq folderId) > 0
		RepositoryData.success(data = isExist)
	} catch (e: Exception) {
		errorGetFolder()
	}

	companion object {
		const val ROOT = "root"
	}

}