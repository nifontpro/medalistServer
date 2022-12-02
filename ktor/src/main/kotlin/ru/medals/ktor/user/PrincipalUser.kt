package ru.medals.ktor.user

import io.ktor.server.auth.*
import ru.medals.domain.user.model.User

/**
 * Класс сотрудника, используемый для авторизации в KTor
 */
data class PrincipalUser(
	val email: String? = null,
	val login: String? = null,
	val firstname: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val hashPassword: String? = null,
	val role: String? = null,
	val imageUrl: String? = null,
	val bio: String? = null,
	val companyId: String? = null,
	val departmentId: String? = null,
	val score: Int? = null,
	val currentScore: Int? = null,
	val awardCount: Int = 0,
	val isMnc: Boolean? = null, // Является ли членом номинационной коммисии

	val id: String = ""
) : Principal {

	fun toUser() = User(
		email = email,
		login = login,
		name = firstname,
		patronymic = patronymic,
		lastname = lastname,
		hashPassword = hashPassword,
		role = role,
		imageUrl = imageUrl,
		bio = bio,
		companyId = companyId,
		departmentId = departmentId,
		score = score,
		currentScore = currentScore,
		awardCount = awardCount,
		isMnc = isMnc,
		id = id
	)
}

fun User.toPrincipalUser() = PrincipalUser(
	email = email,
	login = login,
	firstname = name,
	patronymic = patronymic,
	lastname = lastname,
	hashPassword = hashPassword,
	role = role,
	imageUrl = imageUrl,
	bio = bio,
	companyId = companyId,
	departmentId = departmentId,
	score = score,
	currentScore = currentScore,
	awardCount = awardCount,
	isMnc = isMnc,
	id = id
)
