package com.prafull.employeejournal.domain.repositories

import com.prafull.employeejournal.Resource
import com.prafull.employeejournal.domain.model.Employee
import com.prafull.employeejournal.domain.model.EmployeeDto
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {

    suspend fun getEmployees(): Flow<Resource<List<EmployeeDto>>>
    suspend fun addEmployee(employee: Employee)
    suspend fun deleteEmp(id: Long)
}