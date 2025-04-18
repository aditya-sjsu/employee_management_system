package com.example.employeemanagementsystem.Repository;

import com.example.employeemanagementsystem.Entity.EmployeeProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProjectEntity, Integer> {

}
