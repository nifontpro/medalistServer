@file:Suppress("ConstPropertyName")

package ru.medals.data.user.repository.query

import ru.medals.data.core.*

//fields:
private const val awardState = "\$awardState"
private const val startDate = "\$startDate"
private const val endDate = "\$endDate"

fun getAwardsCountByCompanyQuery(companyId: String): String {

	return """[
		
    {
        $match: { companyId: '$companyId'}
    },
    
		{
        $addFields: {
            awardState: {
                $switch: {
                    branches: [
                        {case: {$gt: ['$startDate', '$endDate']}, then: 'ERROR'},
                        {case: {$lt: ['$NOW', '$startDate']}, then: 'BEFORE'},
                        {case: {$gt: ['$NOW', '$endDate']}, then: 'AFTER'}
                    ],
                    default: 'NOMINEE'
                }
            }
        }
    },

    {
        $group: {
            _id: null,
            before: {$sum: {$cond: {if: {$eq: ['$awardState', 'BEFORE']}, then: 1, else: 0}}},
            nominee: {$sum: {$cond: {if: {$eq: ['$awardState', 'NOMINEE']}, then: 1, else: 0}}},
            after: {$sum: {$cond:{if: {$eq: ['$awardState', 'AFTER']}, then: 1, else: 0}}},
            error: {$sum: {$cond:{if: {$eq: ['$awardState', 'ERROR']}, then: 1, else: 0}}},
            total: {$count: {}}
        }
    },		
		
	]""".trimIndent()
}