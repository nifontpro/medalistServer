package ru.medals.data.user.repository.query

import ru.medals.data.core.*

//fields:
@Suppress("ConstPropertyName")
private const val Relations = "\$relations"

/**
 * Получить только сотрудников с наградами
 */
fun getAwardUsersByCompanyQuery(
	companyId: String,
	searchFilter: String?,
	startDate: Long? = null,
	endDate: Long? = null,
): String {

	val strFilter = if (searchFilter.isNullOrBlank()) "" else
		"""
				, {
            $or: [
                {name: {$regex: '$searchFilter', $options: "i"}},
                {lastname: {$regex: '$searchFilter', $options: "i"}}
            ]
        }			
		""".trimIndent()

	val dataFilter = if (startDate != null && endDate != null) {
		""",
			{$eq: ['${"$$"}rel.state', 'AWARD']},	
			{$gte: ['${"$$"}rel.awardDate', $startDate]},					
			{$lte: ['${"$$"}rel.awardDate', $endDate]},					
		""".trimIndent()
	} else ""

	return """[
    {
        $match: {
            $and: [
                {companyId: {$eq: '$companyId'}}$strFilter
            ]
        }
    },
    {
        $lookup: {
            from: 'awardCol',
            localField: '_id',
            foreignField: 'relations.userId',
            let: {relations: '$Relations', uid: '${'$'}_id'},
            pipeline: [{
                $project: {
                    _id: 1,
                    companyId: 1,
                    name: 1,
                    description: 1,
                    criteria: 1,
                    startDate: 1,
                    endDate: 1,
                    imageUrl: 1,
                    relations: {
                        $filter: {input: '$Relations', as: 'rel', 
													cond: {$and: [
														{$eq: ['${"$$"}rel.userId', '${"$$"}uid']}
														$dataFilter
													]}
												}
                    }
                }
            }, {$unwind: '$Relations'},
                {
                    $replaceRoot: {
                        newRoot: {
                            $mergeObjects: [
                                {
                                    _id: '${'$'}_id',
                                    companyId: '${'$'}companyId',
                                    name: '${'$'}name',
                                    description: '${'$'}description',
                                    imageUrl: '${'$'}imageUrl',
                                    startDate: '${'$'}startDate',
                                    endDate: '${'$'}endDate'
                                }, '${'$'}relations'
                            ]
                        }
                    }
                },
            ],
            as: 'awards'
        }
    },
		
		{$addFields: {
			awardCount: {
        $size: '${'$'}awards'
      }	
		}},
		
		{$match: { $expr: {
			$gt: ['${'$'}awardCount', 0]
		}}},

    {
        $lookup: {
            from: "departmentCol",
            localField: "departmentId",
            foreignField: "_id",
            as: 'department'
        }
    },

    {
        $unwind: {
            path: '${'$'}department',
            preserveNullAndEmptyArrays: true
        }
    },

    {
        $project: {
            awards: 1,
            email: 1,
            login: 1,
            name: 1,
            lastname: 1,
            patronymic: 1,
            role: 1,
            bio: 1,
            post: 1,
            phone: 1,
            gender: 1,
            description: 1,
            companyId: 1,
            departmentId: 1,
            imageUrl: 1,
            imageKey: 1,
            departmentName: '${'$'}department.name',
            awardCount: 1
        }
    },

    {$sort: {awardCount: -1, lastname: 1}}

]""".trimIndent()
}