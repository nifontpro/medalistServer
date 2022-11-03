package ru.medals.ktor.company

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.company.bussines.processor.CompanyProcessor
import ru.medals.domain.user.model.User.Companion.OWNER

fun Route.companyRoutes() {

	val companyProcessor: CompanyProcessor by inject()

	route("api/company") {
		authenticate(OWNER) {
			post("create") {
				call.createCompany(companyProcessor)
			}

			delete {
				call.deleteCompany(companyProcessor)
			}

			post("owner") {
				call.getCompaniesByOwner(companyProcessor)
			}

			put("update") {
				call.updateCompanyProfile(companyProcessor)
			}

			post("count") {
				call.getCountByOwner(companyProcessor)
			}

			put("image/update") {
				call.updateCompanyImageOld(companyProcessor)
			}

			post("image") {
				call.createCompanyImage(companyProcessor)
			}

			put("image") {
				call.updateCompanyImage(companyProcessor)
			}

			delete ("image") {
				call.deleteCompanyImage(companyProcessor)
			}

		}

		post("all") {
			call.getAllCompany(companyProcessor)
		}

		post("get_id") {
			call.getCompanyById(companyProcessor)
		}
	}
}
