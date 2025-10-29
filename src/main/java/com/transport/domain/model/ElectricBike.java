package com.transport.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "electric_bikes")
@NoArgsConstructor
@AllArgsConstructor
public class ElectricBike extends Vehicle {
    @Column(nullable = false)
    private double batteryCapacity; // in Wh
    
    @Column(nullable = false)
    private double maxSpeed; // in km/h
    
    @Column(nullable = false)
    private double range; // in km
    
    @Column(nullable = false)
    private int numberOfGears;
    
    @Column(name = "has_lights")
    private boolean hasLights;
    
    @Column(name = "has_basket")
    private boolean hasBasket;
}
