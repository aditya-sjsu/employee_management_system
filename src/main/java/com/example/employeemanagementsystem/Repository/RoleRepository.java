package com.example.employeemanagementsystem.Repository;

import com.example.employeemanagementsystem.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    public Optional<RoleEntity> findByTitle(String roleName);
}
