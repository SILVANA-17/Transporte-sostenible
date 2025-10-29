package com.transport.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {
    @Column(name = "employee_number", nullable = false, unique = true, length = 20)
    private String employeeNumber;
    
    @Column(nullable = false, length = 50)
    private String position;
    
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;
    
    @Column(name = "termination_date")
    private LocalDate terminationDate;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    
    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone", length = 20)
    private String emergencyContactPhone;
    
    @Column(name = "bank_account_number", length = 50)
    private String bankAccountNumber;
    
    @Column(name = "bank_name", length = 100)
    private String bankName;
    
    @Column(name = "social_security_number", length = 20)
    private String socialSecurityNumber;
    
    @Column(length = 500)
    private String skills;
    
    @Column(length = 1000)
    private String address;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(length = 20)
    private String status; // ACTIVE, ON_LEAVE, TERMINATED, RETIRED
    
    @Column(name = "department", length = 100)
    private String department;
    
    @Column(name = "supervisor_id")
    private Long supervisorId;
    
    @Column(name = "work_email", length = 100, unique = true)
    private String workEmail;
    
    @Column(name = "work_phone", length = 20)
    private String workPhone;
    
    @Column(name = "is_full_time")
    private boolean isFullTime = true;
    
    @Column(name = "vacation_days_remaining")
    private int vacationDaysRemaining;
    
    @Column(name = "sick_days_remaining")
    private int sickDaysRemaining;
    
    @Column(name = "last_promotion_date")
    private LocalDate lastPromotionDate;
    
    @Column(name = "next_review_date")
    private LocalDate nextReviewDate;
    
    @Column(length = 1000)
    private String notes;
}
