package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Entity.DepartmentEntity;
import com.example.employeemanagementsystem.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentEntity> getAllDepartments() {
        return this.departmentRepository.findAll();
    }

    public DepartmentEntity getDepartmentByName(String departmentName) {
        return this.departmentRepository.findByName(departmentName).orElse(null);
    }
}
