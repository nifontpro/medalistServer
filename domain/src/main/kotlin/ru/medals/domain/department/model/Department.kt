package ru.medals.domain.department.model

import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class Department(
	val name: String = "",
	val description: String? = null,
	val companyId: String = "", // id компании

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	val id: String = ""
) : IImages
