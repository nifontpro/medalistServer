package ru.medals.domain.department.model

import ru.medals.domain.image.model.ImageRef

data class Department(
	val name: String = "",
	val description: String? = null,
	val imageUrl: String? = null,
	val images: List<ImageRef> = emptyList(),
	val companyId: String = "", // id компании

	val id: String = ""
)
