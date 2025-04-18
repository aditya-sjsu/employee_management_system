package com.example.employeemanagementsystem.Repository;

import com.example.employeemanagementsystem.Entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
    public Optional<DepartmentEntity> findByName(String departmentName);
}
