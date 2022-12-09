package ru.medals.data.user.repository

import ru.medals.data.core.*

//fields:
private const val RELATIONS = "\$relations"

fun getUserByIdWithAwardsDbRequest(userId: String) =
	"""[
				{$Match: {'_id': '$userId'}},
				
				{$Lookup: {
				from: 'awardCol',
				  localField: '_id',
				  foreignField: 'relations.userId',
				  let: {relations: '$RELATIONS'},
				  pipeline: [
				    {$Project: {_id:1, companyId:1, name:1, description:1, criteria:1, startDate:1, endDate:1, imageUrl:1,
				      relations: {$Filter: {input: '$RELATIONS', as: 'rel', cond: {$Eq: ['${"$$"}rel.userId', '$userId']} }}
				    }},
				    {$Unwind: '$RELATIONS'},
				    {$ReplaceRoot : {newRoot: {$MergeObjects: 
				      [
								{_id: '${'$'}_id', companyId: '${'$'}companyId', name: '${'$'}name', description: '${'$'}description', 
								imageUrl: '${'$'}imageUrl', startDate: '${'$'}startDate', endDate: '${'$'}endDate'}, '$RELATIONS'
				      ]
				    }}},
				  ],
				  as: 'awards'					
				}},
				
				{$Lookup: {
					from: "departmentCol",
				  localField: "departmentId",
				  foreignField: "_id",
				  as: 'department'
				}},
				
				{$Unwind: {
					path: '${'$'}department',
          preserveNullAndEmptyArrays: true
				}},
											
				{$Project: {
					awards: 1, email: 1, login: 1, name: 1, lastname: 1, patronymic: 1, role: 1, bio: 1, post: 1, phone: 1, gender: 1, description: 1, companyId: 1, departmentId: 1, awardCount: 1, imageUrl: 1, imageKey:1,
          departmentName: '${'$'}department.name'					
				}},											
		]"""