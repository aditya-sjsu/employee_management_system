package com.example.employeemanagementsystem.Entity;

import jakarta.persistence.*; // or use javax.persistence.* if using older JPA versions

import java.math.BigDecimal;

@Entity
@Table(name = "salaries")
public class SalaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int salaryId;

    @ManyToOne
    private EmployeeEntity employee;

    private BigDecimal baseSalary;
    private BigDecimal bonus;

    private int salaryYear;
    private int salaryMonth;

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public int getSalaryYear() {
        return salaryYear;
    }

    public void setSalaryYear(int salaryYear) {
        this.salaryYear = salaryYear;
    }

    public int getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(int salaryMonth) {
        this.salaryMonth = salaryMonth;
    }
}
