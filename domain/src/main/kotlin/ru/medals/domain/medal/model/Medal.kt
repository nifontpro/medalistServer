package ru.medals.domain.medal.model

import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class Medal(
	val name: String = "",
	val description: String? = null,
	val companyId: String? = null,
	val isSystem: Boolean = false,
	val score: Int? = null,

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	val id: String = ""
): IImages
