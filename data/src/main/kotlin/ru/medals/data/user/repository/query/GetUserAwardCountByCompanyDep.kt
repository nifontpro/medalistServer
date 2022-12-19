@file:Suppress("ConstPropertyName")

package ru.medals.data.user.repository.query

import ru.medals.data.core.*

//fields:
private const val _id = "\$_id"
private const val relations = "\$relations"
private const val awards = "\$awards"
private const val department = "\$department"

fun getUsersAwardCountByCompanyAggregateQuery(companyId: String): String {

	return """[
		
    {
        $match: { companyId: '$companyId'}
    },
    
		{
        $lookup: {
            from: 'awardCol',
            localField: '_id',
            foreignField: 'relations.userId',
            let: {relations: '$relations', uid: '$_id'},
            pipeline: [{
                $project: {
                    _id: 1,
                    startDate: 1,
                    endDate: 1,
                    relations: {
                        $filter: {input: '$relations', as: 'rel', cond: {$eq: ['${"$$"}rel.userId', '${"$$"}uid']}}
                    }
                }
            }, {$unwind: '$relations'},
                {
                    $replaceRoot: {
                        newRoot: {
                            $mergeObjects: [
                                {
                                    _id: '$_id',
                                }, '$relations'
                            ]
                        }
                    }
                },
            ],
            as: 'awards'
        }
    },

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
            path: '$department',
            preserveNullAndEmptyArrays: true
        }
    },

    {
        $project: {
            departmentId: 1,
            departmentName: '$department.name',
            userAwardCount: {
                $size: {
                    $filter:
                        {input: '$awards', as: 'awards', cond: {$eq: ['${"$$"}awards.state', 'AWARD']}}
                }
            },
            userNomineeCount: {
                $size: {
                    $filter:
                        {input: '$awards', as: 'awards', cond: {$eq: ['${"$$"}awards.state', 'NOMINEE']}}
                }
            },
        }
    },
    {
        $group: {
            _id: {_id: '${'$'}departmentId', name : '${'$'}departmentName'},
            userAwardCount: {$sum: '${'$'}userAwardCount'},
            userNomineeCount: {$sum: '${'$'}userNomineeCount'},
        }
    },
				
    {
        $sort: {'_id.name' : 1 }
    }
		
	]""".trimIndent()
}