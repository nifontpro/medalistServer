package ru.medals.ktor.company.mappers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.company.model.Company

fun CompanyContext.toTransportGetCompanies(): List<Company> = companies

fun CompanyContext.toTransportGetCompany(): Company = company