package ru.medals.data.user.repository.query

import ru.medals.data.core.*

//fields:
@Suppress("ConstPropertyName")
private const val Relations = "\$relations"

fun getUserByIdWithAwardsQuery(userId: String) =
	"""[
				{$match: {'_id': '$userId'}},
				
				{$lookup: {
				from: 'awardCol',
				  localField: '_id',
				  foreignField: 'relations.userId',
				  let: {relations: '$Relations'},
				  pipeline: [
				    {$project: {_id:1, companyId:1, name:1, description:1, criteria:1, startDate:1, endDate:1, imageUrl:1,
				      relations: {$filter: {input: '$Relations', as: 'rel', cond: {$eq: ['${"$$"}rel.userId', '$userId']} }}
				    }},
				    {$unwind: '$Relations'},
				    {$replaceRoot : {newRoot: {$mergeObjects: 
				      [
								{_id: '${'$'}_id', companyId: '${'$'}companyId', name: '${'$'}name', description: '${'$'}description', 
								imageUrl: '${'$'}imageUrl', startDate: '${'$'}startDate', endDate: '${'$'}endDate'}, '$Relations'
				      ]
				    }}},
				  ],
				  as: 'awards'					
				}},
				
				{$lookup: {
					from: "departmentCol",
				  localField: "departmentId",
				  foreignField: "_id",
				  as: 'department'
				}},
				
				{$unwind: {
					path: '${'$'}department',
          preserveNullAndEmptyArrays: true
				}},
											
				{$project: {
					awards: 1, email: 1, login: 1, name: 1, lastname: 1, patronymic: 1, role: 1, bio: 1, post: 1, phone: 1, gender: 1, description: 1, companyId: 1, departmentId: 1, imageUrl: 1, imageKey:1,
          departmentName: '${'$'}department.name',
					awardCount: {
						$size: {$filter:
							{input: '${'$'}awards', as: 'awards', cond: {$eq: ['${"$$"}awards.state', 'AWARD']}} 
						}
					}
				}},											
		]""".trimIndent()