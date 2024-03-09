package com.prafull.employeejournal.domain.model

import com.google.gson.annotations.SerializedName

data class EmployeeDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("email")
    val email: String
) {
    fun toEmployee(): Employee {
        return Employee(
            name = name,
            location = location,
            email = email
        )
    }
}
