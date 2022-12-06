package ru.medals.data.user.repository

import org.bson.BsonDocument
import org.bson.BsonInt32
import org.litote.kmongo.*
import ru.medals.data.award.model.AwardCol
import ru.medals.data.user.model.UserAwardsCol
import ru.medals.data.user.model.UserCol

interface UserProjections {
	companion object {
		val projectUserFieldsWithDepartmentName = project(
			UserCol::id from UserCol::id,
			UserCol::email from UserCol::email,
			UserCol::login from UserCol::login,
			UserCol::name from UserCol::name,
			UserCol::patronymic from UserCol::patronymic,
			UserCol::lastname from UserCol::lastname,
			UserCol::role from UserCol::role,
			UserCol::imageUrl from UserCol::imageUrl,
			UserCol::imageKey from UserCol::imageKey,
			UserCol::bio from UserCol::bio,
			UserCol::post from UserCol::post,
			UserCol::phone from UserCol::phone,
			UserCol::gender from UserCol::gender,
			UserCol::description from UserCol::description,
			UserCol::companyId from UserCol::companyId,
			UserCol::departmentId from UserCol::departmentId,
			UserCol::awardCount from UserCol::awardCount,
			UserCol::departmentName from ("\$department.name")
		)

		val projectUserFieldsWithDepNameAndAwards = project(
			UserAwardsCol::id from 1,
			UserAwardsCol::email from 1,
			UserAwardsCol::login from 1,
			UserAwardsCol::name from 1,
			UserAwardsCol::patronymic from 1,
			UserAwardsCol::lastname from 1,
			UserAwardsCol::role from 1,
			UserAwardsCol::imageUrl from 1,
			UserAwardsCol::imageKey from 1,
//			UserAwardsCol::images from 1,
			UserAwardsCol::post from 1,
			UserAwardsCol::phone from 1,
			UserAwardsCol::gender from 1,
			UserAwardsCol::companyId from 1,
			UserAwardsCol::departmentId from 1,
			UserAwardsCol::awardCount from UserAwardsCol::awardCount,
			UserAwardsCol::departmentName from ("\$department.name"),
			UserAwardsCol::awards / AwardCol::name from 1,
			UserAwardsCol::awards / AwardCol::companyId from 1
		)

		val sortByAwardCountAndLastName = sort(
			BsonDocument()
				.append(UserCol::awardCount.path(), BsonInt32(-1))
				.append(UserCol::lastname.path(), BsonInt32(1))
		)
	}

}