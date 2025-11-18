package co.edu.umanizales.transport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Employee extends User {
    @NotBlank(message = "Error: El ID del empleado no puede estar vacío")
    @Size(min = 3, max = 50, message = "Error: El ID del empleado debe tener entre 3 y 50 caracteres")
    private String employeeId;
    
    @NotBlank(message = "Error: El departamento no puede estar vacío")
    @Size(min = 3, max = 100, message = "Error: El departamento debe tener entre 3 y 100 caracteres")
    private String department;
    
    @NotNull(message = "Error: El salario no puede estar vacío")
    @Positive(message = "Error: El salario debe ser mayor a 0")
    private Double salary;
}
