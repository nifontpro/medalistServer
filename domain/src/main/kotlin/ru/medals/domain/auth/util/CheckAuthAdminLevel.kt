package ru.medals.domain.auth.util

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.company.repository.CompanyRepository
import ru.medals.domain.user.model.User

suspend fun checkAuthMinAdmin(
	companyId: String?,
	principalUser: User
): Boolean {

	if (companyId == null) {
		return false
	}

	val companyRepository: CompanyRepository by inject(CompanyRepository::class.java)

	if (principalUser.role == User.ADMIN && principalUser.companyId != companyId) {
		return false
	}

	if (principalUser.role == User.OWNER &&
		companyRepository.getCompanyById(companyId)?.ownerId != principalUser.id
	) {
		return false
	}
	return true
}