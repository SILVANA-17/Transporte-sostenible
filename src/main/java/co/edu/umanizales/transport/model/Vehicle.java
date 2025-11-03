package co.edu.umanizales.transport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vehicle {
    private long id;
    private String brand;
    private String model;
    private double pricePerHour;
}
