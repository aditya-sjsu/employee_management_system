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

    public EmployeeEntity updateEmployee(EmployeeEntity existingEmployee) {
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

//    public List<EmployeeEntity> searchEmployee(String firstName, String lastName, String email, String phone) {
//        return employeeRepository.findAll().stream()
//                .filter(employee ->
//                        (firstName == null || employee.getFirstName().toLowerCase().contains(firstName.toLowerCase())) &&
//                                (lastName == null || employee.getLastName().toLowerCase().contains(lastName.toLowerCase())) &&
//                                (department == null || employee.getDepartment().getName().toLowerCase().contains(department.toLowerCase())) &&
//                                (role == null || employee.getRole().getTitle().toLowerCase().contains(role.toLowerCase())))
//                .collect(Collectors.toList());
//    }
}
