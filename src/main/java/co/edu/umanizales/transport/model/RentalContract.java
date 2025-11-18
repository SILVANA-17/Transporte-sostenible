package co.edu.umanizales.transport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalContract {
    private long id;
    
    @NotNull(message = "Error: El vehículo no puede estar vacío")
    private Vehicle vehicle;
    
    @NotNull(message = "Error: El cliente no puede estar vacío")
    private Client client;
    
    @NotNull(message = "Error: La fecha de inicio no puede estar vacía")
    private LocalDate startDate;
    
    @NotNull(message = "Error: La fecha de fin no puede estar vacía")
    private LocalDate endDate;
    
    @Positive(message = "Error: El precio total debe ser mayor a 0")
    private double totalPrice;
    
    @NotBlank(message = "Error: El estado del contrato no puede estar vacío")
    @Size(min = 3, max = 50, message = "Error: El estado debe tener entre 3 y 50 caracteres")
    private String status;
    
    @NotNull(message = "Error: El método de pago no puede estar vacío")
    private PaymentMethod paymentMethod;
}
