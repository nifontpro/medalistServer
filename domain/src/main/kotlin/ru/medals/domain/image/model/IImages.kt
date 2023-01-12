package ru.medals.domain.image.model

interface IImages {
	val sysImage: Boolean
	val imageUrl: String?
	val imageKey: String?
	val images: List<ImageRef>
}