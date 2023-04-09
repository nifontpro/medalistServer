package ru.medals.domain.user.model

import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class User(
	val email: String? = null,
	val login: String? = null,
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val hashPassword: String? = null,
	val role: String? = null,
	val bio: String? = null,
	val post: String? = null,
	val phone: String? = null,
	val gender: Gender = Gender.UNDEFINED,
	val description: String? = null,
	val companyId: String? = null,
	val departmentId: String? = null,
	val score: Int? = null,
	val currentScore: Int? = null,
	val awardCount: Int = 0,
	val isMnc: Boolean? = null, // Является ли членом номинационной комиссии
	val departmentName: String? = null,

	override val sysImage: Boolean = false,
	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	val id: String = ""
) : IImages {

	companion object {

		fun getRolePriorityStatic(role: String?): Int {
			return when (role) {
				ROOT -> ROOT_PRIORITY
				SUPER -> SUPER_PRIORITY
				OWNER -> OWNER_PRIORITY
				ADMIN -> ADMIN_PRIORITY
				DIRECTOR -> DIRECTOR_PRIORITY
				USER -> USER_PRIORITY
				GUEST -> GUEST_PRIORITY
				else -> 0
			}
		}

		const val ROOT = "root"
		const val SUPER = "super"
		const val OWNER = "owner"
		const val ADMIN = "admin"
		const val DIRECTOR = "director"
		const val USER = "user"
		const val GUEST = "guest"
		const val NONE = "none"

		const val ROOT_PRIORITY = 888
		const val SUPER_PRIORITY = 777
		const val OWNER_PRIORITY = 90
		const val ADMIN_PRIORITY = 80
		const val DIRECTOR_PRIORITY = 70
		const val USER_PRIORITY = 60
		const val GUEST_PRIORITY = 50
	}

	fun getRolePriority() = getRolePriorityStatic(role)
}