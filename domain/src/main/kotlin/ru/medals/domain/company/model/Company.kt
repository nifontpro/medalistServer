package ru.medals.domain.company.model

import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class Company(
	val name: String = "",
	val description: String? = null,
	val ownerId: String = "", // id владельца компании
	val phone: String? = null,
	val email: String? = null,
	val address: String? = null,

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	val id: String = ""
) : IImages
