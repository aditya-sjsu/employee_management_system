package com.example.employeemanagementsystem.Controller;

import com.example.employeemanagementsystem.DTO.EmployeeRequestDTO;
import com.example.employeemanagementsystem.DTO.EmployeeResponseDTO;
import com.example.employeemanagementsystem.DTO.ErrorResponseDTO;
import com.example.employeemanagementsystem.Entity.DepartmentEntity;
import com.example.employeemanagementsystem.Entity.EmployeeEntity;
import com.example.employeemanagementsystem.Entity.RoleEntity;
import com.example.employeemanagementsystem.Service.DepartmentService;
import com.example.employeemanagementsystem.Service.EmployeeService;
import com.example.employeemanagementsystem.Service.RoleService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        // Logic to fetch all employees
        List<EmployeeEntity> employees = this.employeeService.getAllEmployees();
        List<EmployeeResponseDTO> employeeResponseDTOList = employees.stream()
                .map(employee -> new EmployeeResponseDTO(
                        employee.getEmployeeId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail(),
                        employee.getPhone(),
                        employee.getDepartment().getName(),
                        employee.getRole().getTitle()
                ))
                .toList();
        return ResponseEntity.ok().body(employeeResponseDTOList);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable(required = true) int employeeId) {
        EmployeeEntity employee = this.employeeService.getEmployeeById(employeeId);
        return employee == null ? ResponseEntity.ok().body(Collections.emptyMap()) : ResponseEntity.ok().body(new EmployeeResponseDTO(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDepartment().getName(),
                employee.getRole().getTitle()
        ));
    }

    @PostMapping("/")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        DepartmentEntity department = this.departmentService.getDepartmentByName(employeeRequestDTO.getDepartmentName());
        if (department == null) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(new Date(), "Department with name `" + employeeRequestDTO.getDepartmentName() + "` not found"));
        }

        RoleEntity role = this.roleService.getRoleByTitle(employeeRequestDTO.getRoleName());
        if (role == null) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(new Date(), "Role with name `" + employeeRequestDTO.getRoleName() + "` not found"));
        }
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setFirstName(employeeRequestDTO.getFirstName());
        employeeEntity.setLastName(employeeRequestDTO.getLastName());
        employeeEntity.setEmail(employeeRequestDTO.getEmail());
        employeeEntity.setPhone(employeeRequestDTO.getPhone());
        employeeEntity.setDepartment(department);
        employeeEntity.setRole(role);
        EmployeeEntity savedEmployee = employeeService.addEmployee(employeeEntity);
        return ResponseEntity.ok().body(new EmployeeResponseDTO(
                savedEmployee.getEmployeeId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getEmail(),
                savedEmployee.getPhone(),
                savedEmployee.getDepartment().getName(),
                savedEmployee.getRole().getTitle()
        ));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable int employeeId, @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeEntity existingEmployee = employeeService.getEmployeeById(employeeId);
        if (existingEmployee == null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(new Date(), "Employee with id " + employeeId + " not found"));
        }

        DepartmentEntity department = departmentService.getDepartmentByName(employeeRequestDTO.getDepartmentName());
        if (department == null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(new Date(), "Department with name `" + employeeRequestDTO.getDepartmentName() + "` not found"));
        }

        RoleEntity role = roleService.getRoleByTitle(employeeRequestDTO.getRoleName());
        if (role == null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(new Date(), "Role with name `" + employeeRequestDTO.getRoleName() + "` not found"));
        }

        existingEmployee.setFirstName(employeeRequestDTO.getFirstName());
        existingEmployee.setLastName(employeeRequestDTO.getLastName());
        existingEmployee.setEmail(employeeRequestDTO.getEmail());
        existingEmployee.setPhone(employeeRequestDTO.getPhone());
        existingEmployee.setDepartment(department);
        existingEmployee.setRole(role);

        EmployeeEntity updatedEmployee = employeeService.updateEmployee(existingEmployee);
        return ResponseEntity.ok().body(new EmployeeResponseDTO(
                updatedEmployee.getEmployeeId(),
                updatedEmployee.getFirstName(),
                updatedEmployee.getLastName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getPhone(),
                updatedEmployee.getDepartment().getName(),
                updatedEmployee.getRole().getTitle()
        ));
    }
}
