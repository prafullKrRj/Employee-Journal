package com.prafull.employeejournal.data

import com.prafull.employeejournal.domain.model.Employee
import com.prafull.employeejournal.domain.model.EmployeeDto
import okhttp3.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EmployeeApiService {

    @GET("/employee/getAll/")
    suspend fun getAllEmployees() : List<EmployeeDto>

    @POST("/employee/saveEmployee/")
    suspend fun saveEmployee(
        @Body employee: Employee
    )
    @DELETE("/employee/deleteEmployee/{id}/")
    suspend fun deleteEmployee(
        @Path("id") id: Long
    )
}