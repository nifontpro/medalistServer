package ru.medals.domain.gallery.repository

import ru.medals.domain.core.bussines.model.BaseQueryValid
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.domain.image.model.FileData

interface GalleryRepository {

	suspend fun addItem(galleryItem: GalleryItem, fileData: FileData): RepositoryData<GalleryItem>
	suspend fun getByFolder(folderId: String, baseQuery: BaseQueryValid): RepositoryData<List<GalleryItem>>
	suspend fun delete(item: GalleryItem): RepositoryData<GalleryItem>
	suspend fun getById(id: String): RepositoryData<GalleryItem>
	suspend fun updateImage(item: GalleryItem, fileData: FileData): RepositoryData<Unit>
	suspend fun update(item: GalleryItem): RepositoryData<GalleryItem>
}