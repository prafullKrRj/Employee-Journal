package com.prafull.employeejournalback.model.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
/**
 *  This annotation is used to mark the class as a service provider.
 *  Service is a specialization of the component annotation.
 *  It doesn't currently provide any additional behavior over the @Component annotation,
 *  but it's a good idea to use @Service over @Component in service-layer classes because
 *  it specifies intent better.
 * */
public class EmployeeDao {

    @Autowired
    private EmployeeRepository repo;

    public Employee save(Employee employee) {
        return repo.save(employee);
    }
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        Streamable.of(repo.findAll()).forEach(list::add);
        return list;
    }
    public void delete(Employee employee) {
        repo.delete(employee);
    }
    public void deleteById(int employeeId) {
        repo.deleteById(employeeId);
    }
}
