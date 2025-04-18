package com.example.employeemanagementsystem.Repository;

import com.example.employeemanagementsystem.Entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {

}
