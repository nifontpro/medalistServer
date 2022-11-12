package ru.medals.domain.award.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardMedal
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand

data class AwardContext(

	var award: Award = Award(), // set
	var awardMedal: AwardMedal = AwardMedal(), // get
	var awards: List<AwardMedal> = emptyList(),

	var awardId: String = "",

	) : BaseContext(command = Command.NONE) {

	val awardRepository: AwardRepository by inject(AwardRepository::class.java)

	enum class Command : IBaseCommand {
		NONE,
		CREATE,
		DELETE,
		GET_BY_ID,
		GET_BY_COMPANY,
		UPDATE,
	}
}