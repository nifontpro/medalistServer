@file:Suppress("ConstPropertyName")

package ru.medals.data.user.repository.query

import ru.medals.data.core.*

//fields:
private const val _id = "\$_id"
private const val relations = "\$relations"
private const val awards = "\$awards"

fun getAwardCountByEntity(field: String, value: String): String {

//	$match: { companyId: '63641544e81cbd0b5c5a8412'}

	return """[
		
    {
        $match: { $field: '$value'}
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
        $project: {
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
            _id: null,
            awards: {$sum: '${'$'}userAwardCount'},
            nominee: {$sum: '${'$'}userNomineeCount'},
        }
    },
    {
        $addFields: {
            total: {$add: ['${'$'}awards', '${'$'}nominee']}
        }
    }		
		
	]""".trimIndent()
}