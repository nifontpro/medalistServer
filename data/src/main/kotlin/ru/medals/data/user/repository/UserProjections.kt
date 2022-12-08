package ru.medals.data.user.repository

import org.bson.BsonDocument
import org.bson.BsonInt32
import org.litote.kmongo.*
import ru.medals.data.award.model.AwardCol
import ru.medals.data.user.model.UserAwardsLiteCol
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
			UserAwardsLiteCol::id from 1,
			UserAwardsLiteCol::email from 1,
			UserAwardsLiteCol::login from 1,
			UserAwardsLiteCol::name from 1,
			UserAwardsLiteCol::patronymic from 1,
			UserAwardsLiteCol::lastname from 1,
			UserAwardsLiteCol::role from 1,
			UserAwardsLiteCol::imageUrl from 1,
			UserAwardsLiteCol::imageKey from 1,
//			UserAwardsCol::images from 1,
			UserAwardsLiteCol::post from 1,
			UserAwardsLiteCol::phone from 1,
			UserAwardsLiteCol::gender from 1,
			UserAwardsLiteCol::companyId from 1,
			UserAwardsLiteCol::departmentId from 1,
			UserAwardsLiteCol::awardCount from UserAwardsLiteCol::awardCount,
			UserAwardsLiteCol::departmentName from ("\$department.name"),

			UserAwardsLiteCol::awards / AwardCol::name from 1,
			UserAwardsLiteCol::awards / AwardCol::imageUrl from 1,
			UserAwardsLiteCol::awards / AwardCol::companyId from 1
		)

		val sortByAwardCountAndLastName = sort(
			BsonDocument()
				.append(UserCol::awardCount.path(), BsonInt32(-1))
				.append(UserCol::lastname.path(), BsonInt32(1))
		)
	}

}