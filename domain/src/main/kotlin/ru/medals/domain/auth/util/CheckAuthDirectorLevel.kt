package ru.medals.domain.auth.util

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.company.repository.CompanyRepository
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.User.Companion.ADMIN
import ru.medals.domain.user.model.User.Companion.DIRECTOR

suspend fun checkAuthMinDirector(
	companyId: String?,
	departmentId: String?,
	principalUser: User
): Boolean {
	val companyRepository: CompanyRepository by inject(CompanyRepository::class.java)

	if (principalUser.role == DIRECTOR && principalUser.companyId != companyId &&
		principalUser.departmentId != departmentId
	) {
		return false
	}

	if (principalUser.role == ADMIN && principalUser.companyId != companyId) {
		return false
	}

	// Проверка, принадлежит ли компания владельцу
	if (principalUser.role == User.OWNER && companyId != null &&
		companyRepository.getCompanyById(companyId)?.ownerId != principalUser.id
	) {
		return false
	}
	return true
}