package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Entity.DepartmentEntity;
import com.example.employeemanagementsystem.Entity.RoleEntity;
import com.example.employeemanagementsystem.Repository.DepartmentRepository;
import com.example.employeemanagementsystem.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity getRoleByTitle(String roleName) {
        return this.roleRepository.findByTitle(roleName).orElse(null);
    }
}
