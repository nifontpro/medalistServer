package ru.medals.domain.company.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.company.model.Company
import ru.medals.domain.company.repository.CompanyRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.department.repository.DepartmentRepository

data class CompanyContext(

	var company: Company = Company(),
	var companies: List<Company> = emptyList(),

	) : BaseContext(command = CompanyCommand.NONE) {

	val companyRepository: CompanyRepository by inject(CompanyRepository::class.java)
	val departmentRepository: DepartmentRepository by inject(DepartmentRepository::class.java)
}

enum class CompanyCommand : IBaseCommand {
	NONE,
	CREATE_EMPTY,
	CREATE,
	DELETE,
	GET_BY_ID,
	GET_ALL,
	GET_BY_OWNER,
	GET_COUNT_BY_OWNER,
	UPDATE,
	UPDATE_MAIN_IMAGE,
	DELETE_MAIN_IMAGE,
	IMAGE_ADD,
	IMAGE_UPDATE,
	IMAGE_DELETE,
	GET_IDS
}