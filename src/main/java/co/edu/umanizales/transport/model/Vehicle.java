package co.edu.umanizales.transport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vehicle {
    private long id;
    
    @NotBlank(message = "Error: La marca del vehículo no puede estar vacía")
    @Size(min = 2, max = 50, message = "Error: La marca debe tener entre 2 y 50 caracteres")
    private String brand;
    
    @NotBlank(message = "Error: El modelo del vehículo no puede estar vacío")
    @Size(min = 2, max = 50, message = "Error: El modelo debe tener entre 2 y 50 caracteres")
    private String model;
    
    @Positive(message = "Error: El precio por hora debe ser mayor a 0")
    private double pricePerHour;
}
