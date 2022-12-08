package ru.medals.domain.user.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.company.repository.CompanyRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.UserAwardsLite
import ru.medals.domain.user.repository.UserRepository

data class UserContext(

    var user: User = User(),
    val users: MutableList<User> = mutableListOf(),
    var usersAwards: List<UserAwardsLite> = emptyList()

) : BaseContext(command = UserCommand.NONE) {

    val userRepository: UserRepository by inject(UserRepository::class.java)
    val companyRepository: CompanyRepository by inject(CompanyRepository::class.java)
}

enum class UserCommand : IBaseCommand {
    NONE,
    CREATE,
    GET_BY_ID,
    GET_BY_ID_DEP_NAME,
    GET_BY_DEPARTMENT,
    GET_BY_COMPANY,
    GET_BY_COMPANY_DEP_NAME,
    GET_WITH_AWARDS,
    GET_BOSSES,
    GET_BEST,
    UPDATE,
    UPDATE_IMAGE,
    DELETE,
    COUNT_BY_COMPANY,
    COUNT_BY_DEPARTMENT,
    IMAGE_ADD,
    IMAGE_UPDATE,
    IMAGE_DELETE
}