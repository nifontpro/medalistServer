package ru.medals.domain.auth.util

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.company.repository.CompanyRepository
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.User.Companion.OWNER

suspend fun checkAuthMinOwner(
	companyId: String?,
	principalUser: User
): Boolean {
	if (companyId == null) {
		return false
	}

	val companyRepository: CompanyRepository by inject(CompanyRepository::class.java)

	if (principalUser.role != OWNER) {
		return false
	}

	// Проверка, принадлежит ли компания владельцу
	if (companyRepository.getCompanyById(companyId)?.ownerId != principalUser.id) {
		return false
	}
	return true
}