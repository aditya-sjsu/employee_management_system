package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Entity.EmployeeEntity;
import com.example.employeemanagementsystem.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeEntity> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findAll();
        return employees;
    }

    public EmployeeEntity getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public EmployeeEntity addEmployee(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }
}
