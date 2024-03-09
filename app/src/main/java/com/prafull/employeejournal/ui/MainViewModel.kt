package com.prafull.employeejournal.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.employeejournal.Resource
import com.prafull.employeejournal.domain.model.Employee
import com.prafull.employeejournal.domain.model.EmployeeDto
import com.prafull.employeejournal.domain.repositories.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: EmployeeRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<Resource<List<EmployeeDto>?>> = MutableStateFlow(Resource.Loading())
    val uiState = _uiState.asStateFlow()
    val newEmployee = mutableStateOf(Employee())
    init {
        getAllEmployees()
    }
    fun addEmployee() {

    }
    fun getAllEmployees() {
        viewModelScope.launch {
            repository.getEmployees().collect { response ->
                when(response) {
                    is Resource.Error -> {
                        _uiState.update {
                            Resource.Error(response.message.toString())
                        }
                    }
                    is Resource.Loading -> {
                        _uiState.update {
                            Resource.Loading()
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            Resource.Success(response.data)
                        }
                    }
                }
            }
        }
    }

    fun delete(employee: EmployeeDto) {
        viewModelScope.launch {
            repository.deleteEmp(employee.id)
            getAllEmployees()
        }
    }
}