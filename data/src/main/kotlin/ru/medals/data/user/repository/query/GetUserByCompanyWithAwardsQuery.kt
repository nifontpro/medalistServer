package ru.medals.data.user.repository.query

import ru.medals.data.core.*

//fields:
@Suppress("ConstPropertyName")
private const val Relations = "\$relations"

fun getUsersByCompanyWithAwardsQuery(companyId: String, filter: String?): String {

	val strFilter = if (filter.isNullOrBlank()) "" else
		"""
				, {
            $Or: [
                {name: {$Regex: '$filter'}},
                {lastname: {$Regex: '$filter'}}
            ]
        }			
		""".trimIndent()

	return """[
    {
        $Match: {
            $And: [
                {companyId: {$Eq: '$companyId'}}$strFilter
            ]
        }
    },
    {
        $Lookup: {
            from: 'awardCol',
            localField: '_id',
            foreignField: 'relations.userId',
            let: {relations: '$Relations', uid: '${'$'}_id'},
            pipeline: [{
                $Project: {
                    _id: 1,
                    companyId: 1,
                    name: 1,
                    description: 1,
                    criteria: 1,
                    startDate: 1,
                    endDate: 1,
                    imageUrl: 1,
                    relations: {
                        $Filter: {input: '$Relations', as: 'rel', cond: {$Eq: ['${"$$"}rel.userId', '${"$$"}uid']}}
                    }
                }
            }, {$Unwind: '$Relations'},
                {
                    $ReplaceRoot: {
                        newRoot: {
                            $MergeObjects: [
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

    {
        $Lookup: {
            from: "departmentCol",
            localField: "departmentId",
            foreignField: "_id",
            as: 'department'
        }
    },

    {
        $Unwind: {
            path: '${'$'}department',
            preserveNullAndEmptyArrays: true
        }
    },

    {
        $Project: {
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
            awardCount: {
                $Size: {
                    $Filter:
                        {input: '${'$'}awards', as: 'awards', cond: {$Eq: ['${"$$"}awards.state', 'AWARD']}}
                }
            }
        }
    },

    {$Sort: {awardCount: -1, lastname: 1}}

]""".trimIndent()
}