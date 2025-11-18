package co.edu.umanizales.transport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    private long id;
    
    @NotBlank(message = "Error: El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "Error: El nombre debe tener entre 3 y 100 caracteres")
    private String name;
    
    @NotBlank(message = "Error: El email no puede estar vacío")
    @Email(message = "Error: El email no es válido")
    private String email;
}
