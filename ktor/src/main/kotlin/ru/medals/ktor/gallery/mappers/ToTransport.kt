package ru.medals.ktor.gallery.mappers

import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.model.GalleryItem

fun GalleryContext.toTransportGetGallery(): List<GalleryItem> = gallery

fun GalleryContext.toTransportGetGalleryItem(): GalleryItem = galleryItem