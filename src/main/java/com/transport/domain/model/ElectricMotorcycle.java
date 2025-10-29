package com.transport.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "electric_motorcycles")
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("MOTORCYCLE")
public class ElectricMotorcycle extends Vehicle {
    @Column(name = "battery_capacity", nullable = false)
    private double batteryCapacity; // in kWh
    
    @Column(name = "max_speed", nullable = false)
    private double maxSpeed; // in km/h
    
    @Column(nullable = false)
    private double range; // in km
    
    @Column(name = "charging_time")
    private double chargingTime; // in hours
    
    @Column(name = "has_storage")
    private boolean hasStorage;
    
    @Column(name = "max_load")
    private double maxLoad; // in kg
    
    @Column(name = "is_license_required")
    private boolean isLicenseRequired;
}
