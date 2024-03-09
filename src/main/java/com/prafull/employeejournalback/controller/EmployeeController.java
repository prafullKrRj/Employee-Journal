package com.prafull.employeejournalback.controller;

import com.prafull.employeejournalback.model.employee.Employee;
import com.prafull.employeejournalback.model.employee.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeDao dao;

    @GetMapping("/employee/getAll/")
    public List<Employee> getAllEmployees() {
        System.out.println("Getting all employees");
        return dao.getAllEmployees();
    }
    @PostMapping("/employee/saveEmployee/")
    public void saveEmployee(@RequestBody Employee employee) {
        System.out.println("Saving employee");
        dao.save(employee);
    }

    @DeleteMapping("/employee/deleteEmployee/{id}/")
    public ResponseEntity<String> deleteEmployee(@PathVariable  Integer id) {
        try {
            System.out.println("deleting employee with id: " + id);
            dao.deleteById(id);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Employee with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
