package ru.medals.domain.company.model

import ru.medals.domain.image.model.ImageRef

data class Company(
	val name: String = "",
	val description: String? = null,

	val ownerId: String = "", // id владельца компании
	val imageUrl: String? = null,
	val images: List<ImageRef> = emptyList(),

	val id: String = ""
)
