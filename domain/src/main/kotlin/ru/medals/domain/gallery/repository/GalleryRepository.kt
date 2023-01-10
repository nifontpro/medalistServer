package ru.medals.domain.gallery.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.domain.image.model.FileData

interface GalleryRepository {
	suspend fun addItem(galleryItem: GalleryItem, fileData: FileData): RepositoryData<Unit>
}