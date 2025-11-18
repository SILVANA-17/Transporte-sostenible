package co.edu.umanizales.transport.model;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record Invoice(
    @NotNull(message = "Error: El ID de la factura no puede estar vacío")
    @Positive(message = "Error: El ID de la factura debe ser mayor a 0")
    Long invoiceId,
    
    @NotNull(message = "Error: La fecha de la factura no puede estar vacía")
    LocalDate date,
    
    @NotNull(message = "Error: El valor total no puede estar vacío")
    @Positive(message = "Error: El valor total debe ser mayor a 0")
    Double totalValue
) {}
