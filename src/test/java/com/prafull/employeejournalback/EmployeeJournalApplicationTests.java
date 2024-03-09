package com.prafull.employeejournalback;

import com.prafull.employeejournalback.model.employee.Employee;
import com.prafull.employeejournalback.model.employee.EmployeeDao;
import com.prafull.employeejournalback.model.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class EmployeeJournalApplicationTests {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    void addEmployeeTest() {
        Employee employee = new Employee();
        employee.setName("Ranjan");
        employee.setEmail("random@gmail.com");
        employee.setLocation("Delhi");
        employeeDao.save(employee);
    }
    @Test
    void getAllEmployees() {
        List<Employee> employees = employeeDao.getAllEmployees();
        System.out.println(employees);
    }
    @Test
    void deleteAllEmployee() {
        List<Employee> employees = employeeDao.getAllEmployees();
        for (Employee employee : employees) {
            employeeDao.delete(employee);
        }
    }
}
