package ru.medals.ktor.gallery

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.gallery.bussines.processor.GalleryProcessor

fun Route.galleryRoutes() {

	val galleryProcessor: GalleryProcessor by inject()

	route("gallery") {

//		authenticate(SUPER) {

			post("create") {
				call.createGalleryItem(galleryProcessor)
			}
//		}
	}
}