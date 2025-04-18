package com.example.employeemanagementsystem.Repository;

import com.example.employeemanagementsystem.Entity.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryEntity, Integer> {

}
