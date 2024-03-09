package com.prafull.employeejournal.data
import com.prafull.employeejournal.Resource
import com.prafull.employeejournal.domain.model.Employee
import com.prafull.employeejournal.domain.model.EmployeeDto
import com.prafull.employeejournal.domain.repositories.EmployeeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmployeeRepoImp @Inject constructor(
    private val apiService: EmployeeApiService
): EmployeeRepository{
    override suspend fun getEmployees(): Flow<Resource<List<EmployeeDto>>> {
        return callbackFlow {
            trySend(Resource.Loading())
            try {
                val employees = apiService.getAllEmployees()
                trySend(Resource.Success(employees))
            } catch (e: Exception) {
                trySend(Resource.Error(e.message.toString()))
            }
            awaitClose { }
        }
    }
    override suspend fun addEmployee(employee: Employee) {
         apiService.saveEmployee(employee)
    }

    override suspend fun deleteEmp(id: Long) {
        apiService.deleteEmployee(id)
    }
}