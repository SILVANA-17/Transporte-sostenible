package com.transport.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "identification_number", unique = true, length = 20)
    private String identificationNumber;
    
    @Column(name = "driver_license_number", length = 50)
    private String driverLicenseNumber;
    
    @Column(name = "driver_license_expiry")
    private LocalDate driverLicenseExpiry;
    
    @Column(name = "is_vip")
    private boolean isVip;
    
    @Column(name = "membership_start_date")
    private LocalDate membershipStartDate;
    
    @Column(name = "membership_end_date")
    private LocalDate membershipEndDate;
    
    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone", length = 20)
    private String emergencyContactPhone;
    
    @Column(length = 500)
    private String preferences;
    
    @Column(name = "total_rentals", columnDefinition = "integer default 0")
    private int totalRentals;
    
    @Column(name = "total_spent", columnDefinition = "decimal(10,2) default 0.00")
    private double totalSpent;
    
    @Column(length = 20)
    private String status; // ACTIVE, INACTIVE, SUSPENDED
}
