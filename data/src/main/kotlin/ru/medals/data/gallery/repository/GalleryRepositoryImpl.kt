package ru.medals.data.gallery.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.gallery.model.GalleryCol
import ru.medals.domain.gallery.repository.GalleryRepository

class GalleryRepositoryImpl(
	db: CoroutineDatabase,
) : GalleryRepository {
	private val gallery = db.getCollection<GalleryCol>()


}