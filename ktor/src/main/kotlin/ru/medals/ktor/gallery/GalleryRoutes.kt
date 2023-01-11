package ru.medals.ktor.gallery

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.gallery.bussines.processor.GalleryProcessor
import ru.medals.domain.user.model.User.Companion.SUPER

fun Route.galleryRoutes() {

	val galleryProcessor: GalleryProcessor by inject()

	route("gallery") {

		authenticate(SUPER) {

			post {
				call.createGalleryItem(galleryProcessor)
			}

			delete {
				call.deleteGalleryItem(galleryProcessor)
			}
		}

		post("get_folder") {
			call.getGalleryByFolder(galleryProcessor)
		}
	}
}