package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.Controller.EmployeeController;
import com.example.employeemanagementsystem.DTO.EmployeeRequestDTO;
import com.example.employeemanagementsystem.DTO.EmployeeResponseDTO;
import com.example.employeemanagementsystem.DTO.ErrorResponseDTO;
import com.example.employeemanagementsystem.Entity.DepartmentEntity;
import com.example.employeemanagementsystem.Entity.EmployeeEntity;
import com.example.employeemanagementsystem.Entity.RoleEntity;
import com.example.employeemanagementsystem.Service.DepartmentService;
import com.example.employeemanagementsystem.Service.EmployeeService;
import com.example.employeemanagementsystem.Service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployeesSuccessLength2() {
        // Arrange
        DepartmentEntity department1 = new DepartmentEntity();
        department1.setDepartmentId(1);
        department1.setName("IT");

        RoleEntity role1 = new RoleEntity();
        role1.setTitle("Admin");
        role1.setRoleId(1);

        RoleEntity role2 = new RoleEntity();
        role2.setTitle("Employee");
        role2.setRoleId(2);

        DepartmentEntity department2 = new DepartmentEntity();
        department2.setName("HR");
        department2.setDepartmentId(2);

        EmployeeEntity employee1 = new EmployeeEntity();
        employee1.setEmployeeId(1);
        employee1.setFirstName("Aditya");
        employee1.setLastName("Shanbhog");
        employee1.setEmail("adityashanbhog@gmail.com");
        employee1.setPhone("123456789");
        employee1.setDepartment(department1);
        employee1.setRole(role1);

        EmployeeEntity employee2 = new EmployeeEntity();
        employee2.setEmployeeId(2);
        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setEmail("jane.doe@example.com");
        employee2.setPhone("987654321");
        employee2.setDepartment(department2);
        employee2.setRole(role2);

        List<EmployeeEntity> mockEmployees = Arrays.asList(employee1, employee2);
        when(employeeService.getAllEmployees()).thenReturn(mockEmployees);

        ResponseEntity<List<EmployeeResponseDTO>> response = employeeController.getAllEmployees();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetAllEmployeesSuccessEmptyResponse() {

        when(employeeService.getAllEmployees()).thenReturn(new ArrayList<>());

        ResponseEntity<List<EmployeeResponseDTO>> response = employeeController.getAllEmployees();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(new ArrayList<>(), response.getBody());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeByIdNotFound() {

        int employeeId = 999;
        when(employeeService.getEmployeeById(employeeId)).thenReturn(null);

        ResponseEntity<?> response = employeeController.getEmployeeById(employeeId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Collections.emptyMap(), response.getBody());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void testGetEmployeeByIdFound() {
        int employeeId = 1;

        DepartmentEntity department = new DepartmentEntity();
        department.setDepartmentId(1);
        department.setName("IT");

        RoleEntity role = new RoleEntity();
        role.setRoleId(1);
        role.setTitle("Developer");

        EmployeeEntity employee = new EmployeeEntity();
        employee.setEmployeeId(employeeId);
        employee.setFirstName("Aditya");
        employee.setLastName("Shanbhog");
        employee.setEmail("adityashanbhog@gmail.com");
        employee.setPhone("123456789");
        employee.setDepartment(department);
        employee.setRole(role);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        ResponseEntity<?> response = employeeController.getEmployeeById(employeeId);

        assertEquals(200, response.getStatusCodeValue());
        EmployeeResponseDTO responseBody = (EmployeeResponseDTO) response.getBody();
        assertEquals(employeeId, responseBody.getEmployeeId());
        assertEquals("Aditya", responseBody.getFirstName());
        assertEquals("Shanbhog", responseBody.getLastName());
        assertEquals("adityashanbhog@gmail.com", responseBody.getEmail());
        assertEquals("123456789", responseBody.getPhone());
        assertEquals("IT", responseBody.getDepartmentName());
        assertEquals("Developer", responseBody.getRoleName());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }
    @Test
    void testAddEmployeeSuccess() {
        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
                "Aditya",
                "Shanbhog",
                "adityashanbhog@gmail.com",
                "123456789",
                "IT",
                "Developer"
        );

        DepartmentEntity department = new DepartmentEntity();
        department.setDepartmentId(1);
        department.setName("IT");

        RoleEntity role = new RoleEntity();
        role.setRoleId(1);
        role.setTitle("Developer");

        EmployeeEntity savedEmployee = new EmployeeEntity();
        savedEmployee.setEmployeeId(1);
        savedEmployee.setFirstName("Aditya");
        savedEmployee.setLastName("Shanbhog");
        savedEmployee.setEmail("adityashanbhog@gmail.com");
        savedEmployee.setPhone("123456789");
        savedEmployee.setDepartment(department);
        savedEmployee.setRole(role);

        when(departmentService.getDepartmentByName("IT")).thenReturn(department);
        when(roleService.getRoleByTitle("Developer")).thenReturn(role);
        when(employeeService.addEmployee(any(EmployeeEntity.class))).thenReturn(savedEmployee);

        // Act
        ResponseEntity<?> response = employeeController.addEmployee(requestDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        EmployeeResponseDTO responseBody = (EmployeeResponseDTO) response.getBody();
        assertEquals(1, responseBody.getEmployeeId());
        assertEquals("Aditya", responseBody.getFirstName());
        assertEquals("Shanbhog", responseBody.getLastName());
        assertEquals("adityashanbhog@gmail.com", responseBody.getEmail());
        assertEquals("123456789", responseBody.getPhone());
        assertEquals("IT", responseBody.getDepartmentName());
        assertEquals("Developer", responseBody.getRoleName());
        verify(departmentService, times(1)).getDepartmentByName("IT");
        verify(roleService, times(1)).getRoleByTitle("Developer");
        verify(employeeService, times(1)).addEmployee(any(EmployeeEntity.class));
    }

    @Test
    void testAddEmployeeDepartmentIsNull() {
        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
                "Aditya",
                "Shanbhog",
                "adityashanbhog@gmail.com",
                "123456789",
                "NonExistentDepartment",
                "Developer"
        );

        when(departmentService.getDepartmentByName("NonExistentDepartment")).thenReturn(null);

        // Act
        ResponseEntity<?> response = employeeController.addEmployee(requestDTO);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        ErrorResponseDTO responseBody = (ErrorResponseDTO) response.getBody();
        assertEquals("Department with name `NonExistentDepartment` not found", responseBody.getMessage());
        verify(departmentService, times(1)).getDepartmentByName("NonExistentDepartment");
        verify(roleService, never()).getRoleByTitle(anyString());
        verify(employeeService, never()).addEmployee(any(EmployeeEntity.class));
    }

    @Test
    void testAddEmployeeRoleIsNull() {
        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
                "Aditya",
                "Shanbhog",
                "adityashanbhog@gmail.com",
                "123456789",
                "IT",
                "NonExistentRole"
        );

        DepartmentEntity department = new DepartmentEntity();
        department.setDepartmentId(1);
        department.setName("IT");

        when(departmentService.getDepartmentByName("IT")).thenReturn(department);
        when(roleService.getRoleByTitle("NonExistentRole")).thenReturn(null);

        // Act
        ResponseEntity<?> response = employeeController.addEmployee(requestDTO);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        ErrorResponseDTO responseBody = (ErrorResponseDTO) response.getBody();
        assertEquals("Role with name `NonExistentRole` not found", responseBody.getMessage());
        verify(departmentService, times(1)).getDepartmentByName("IT");
        verify(roleService, times(1)).getRoleByTitle("NonExistentRole");
        verify(employeeService, never()).addEmployee(any(EmployeeEntity.class));
    }

    @Test
    void testUpdateEmployeeEmployeeNotFound() {
        // Arrange
        int employeeId = 999;
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
            "Aditya",
            "Shanbhog",
            "email@gmail.com",
            "123456789",
            "department",
            "role"
        );

        when(employeeService.getEmployeeById(employeeId)).thenReturn(null);
        ResponseEntity<?> response = employeeController.updateEmployee(employeeId, requestDTO);
        assertEquals(400, response.getStatusCodeValue());
        ErrorResponseDTO responseBody = (ErrorResponseDTO) response.getBody();
        assertEquals("Employee with id 999 not found", responseBody.getMessage());
    }

    @Test
    void testUpdateEmployeeDepartmentNotFound() {
        // Arrange
        int employeeId = 1;
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
                "Aditya",
                "Shanbhog",
                "adityashanbhog@gmail.com",
                "123456789",
                "NonExistentDepartment",
                "Developer"
        );

        // Create existing employee
        DepartmentEntity oldDepartment = new DepartmentEntity();
        oldDepartment.setDepartmentId(1);
        oldDepartment.setName("IT");

        RoleEntity oldRole = new RoleEntity();
        oldRole.setRoleId(1);
        oldRole.setTitle("Developer");

        EmployeeEntity existingEmployee = new EmployeeEntity();
        existingEmployee.setEmployeeId(employeeId);
        existingEmployee.setFirstName("Old Name");
        existingEmployee.setLastName("Old Last");
        existingEmployee.setEmail("old@email.com");
        existingEmployee.setPhone("987654321");
        existingEmployee.setDepartment(oldDepartment);
        existingEmployee.setRole(oldRole);

        // Mock service calls
        when(employeeService.getEmployeeById(employeeId)).thenReturn(existingEmployee);
        when(departmentService.getDepartmentByName("NonExistentDepartment")).thenReturn(null);

        // Act
        ResponseEntity<?> response = employeeController.updateEmployee(employeeId, requestDTO);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        ErrorResponseDTO responseBody = (ErrorResponseDTO) response.getBody();
        assertEquals("Department with name `NonExistentDepartment` not found", responseBody.getMessage());

        // Verify service calls
        verify(employeeService, times(1)).getEmployeeById(employeeId);
        verify(departmentService, times(1)).getDepartmentByName("NonExistentDepartment");
        verify(roleService, never()).getRoleByTitle(anyString());
        verify(employeeService, never()).updateEmployee(any(EmployeeEntity.class));
    }
}