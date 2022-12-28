@file:Suppress("ConstPropertyName")

package ru.medals.data.activity.repository.query

import ru.medals.data.core.*

//fields:
private const val user = "\$user"
private const val award = "\$award"
private const val department = "\$department"

fun getActivityQuery(companyId: String, startDate: Long, endDate: Long, filter: String?): String {
	return """[
		
	{$match: {
		$and: [
      {companyId: '$companyId'},
      {date: {$gte: $startDate, $lte: $endDate}}
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
	      name: 1,
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
	
	{$match: {
    $or: [
	    {'departmentName': {$regex: '$filter'}},
	    {'user.lastname': {$regex: '$filter'}},
	    {'user.name': {$regex: '$filter'}}
    ]	
	}}
		
	]""".trimIndent()
}