package ru.medals.domain.medal.model

import ru.medals.domain.image.model.ImageRef

data class Medal(
	val name: String = "",
	val description: String? = null,
	val companyId: String? = null,
	val isSystem: Boolean = false,
	val score: Int? = null,
	val imageUrl: String? = null,
	val images: List<ImageRef> = emptyList(),

	val id: String = ""

)
