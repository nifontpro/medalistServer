@file:Suppress("ConstPropertyName")

package ru.medals.data.activity.repository.query

import ru.medals.data.core.*
import ru.medals.domain.activity.model.ActivityQuery

//fields:
private const val user = "\$user"
private const val award = "\$award"
private const val department = "\$department"

fun getActivityQuery(activityQuery: ActivityQuery): String {

	val filterStep = activityQuery.filter?.let {
		if (it.isBlank()) return@let ""
		"""
			{$match: {
			    $or: [
				    {'departmentName': {$regex: '$it', $options: "i"}},
				    {'user.lastname': {$regex: '$it', $options: "i"}},
				    {'user.name': {$regex: '$it', $options: "i"}}
			    ]	
			}},			
		""".trimIndent()
	} ?: ""

	val paginationStep = if (activityQuery.page != null && activityQuery.pageSize != null) {
		val skipSize = (activityQuery.page ?: 0) * (activityQuery.pageSize ?: 0)
		""",
			{$skip: $skipSize},
			{$limit: ${activityQuery.pageSize}}
		""".trimIndent()
	} else {
		""
	}

	return """[
		
	{$match: {
		$and: [
      {companyId: '${activityQuery.companyId}'},
      {date: {$gte: ${activityQuery.startDate}, $lte: ${activityQuery.endDate}}}
    ]
	}},
	
	{$lookup: {
		from: 'userCol',
	  localField: 'userId',
	  foreignField: '_id',
	  pipeline: [
	    {$project: {
	      _id:1,
	      name: 1,
	      lastname: 1,
	      patronymic: 1,
	      departmentId: 1,
				post: 1,
	      imageUrl: 1
	    }}  
	  ],
	  as: 'user'	
	}},
	
	{$unwind: {
		path: '$user',
		preserveNullAndEmptyArrays: true
	}},
	
	{$lookup: {
		from: 'awardCol',
	  localField: 'awardId',
	  foreignField: '_id',
	  pipeline: [
	    {$project: {
	      _id:1,
	      name: 1,
	      imageUrl: 1
	    }}  
	  ],
	  as: 'award'
	}},
	
	{$unwind: {
		path: '$award',
		preserveNullAndEmptyArrays: true
	}},
	
	{$lookup: {
		from: 'departmentCol',
	  localField: 'user.departmentId',
	  foreignField: '_id',
	  pipeline: [
	    {$project: {
	      name: 1
	    }}  
	  ],
	  as: 'department'	
	}},
	
	{$unwind: {
		path: '$department',
		preserveNullAndEmptyArrays: true
	}},
	
	{$project: {
		_id: 1,
	  user: 1,
	  award: 1,
		companyId: 1,
	  state: 1,
	  date: 1,
	  departmentName: '$department.name'		
	}},
	
	$filterStep
	
	{$sort: {
		date: ${activityQuery.sort}			
	}}
	
	$paginationStep
	
	]""".trimIndent()
}

// 'user.lastname': 1,