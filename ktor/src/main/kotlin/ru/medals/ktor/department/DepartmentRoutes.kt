package ru.medals.ktor.department

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.department.bussines.processor.DepartmentProcessor
import ru.medals.domain.user.model.User.Companion.ADMIN
import ru.medals.domain.user.model.User.Companion.DIRECTOR

fun Route.departmentRoutes() {

	val departmentProcessor: DepartmentProcessor by inject()

	route("department") {
		authenticate(ADMIN) {
			post("create") {
				call.createEmptyDepartment(departmentProcessor)
			}

			post {
				call.createDepartment(departmentProcessor)
			}

			delete {
				call.deleteDepartment(departmentProcessor)
			}
		}

		authenticate(DIRECTOR) {
			put("update") {
				call.departmentUpdate(departmentProcessor)
			}

			put("image/update") {
				call.updateDepartmentImageOld(departmentProcessor)
			}

			post("image") {
				call.createDepartmentImage(departmentProcessor)
			}

			put("image") {
				call.updateDepartmentImage(departmentProcessor)
			}

			delete("image") {
				call.deleteDepartmentImage(departmentProcessor)
			}
		}

		post("get_company") {
			call.getDepartmentsByCompany(departmentProcessor)
		}

		post("count") {
			call.getDepartmentCount(departmentProcessor)
		}

		post("get_id") {
			call.getDepartmentById(departmentProcessor)
		}

		post("ids") {
			call.getDepartmentIds(departmentProcessor)
		}
	}

}
