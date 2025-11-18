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
public class Client extends User {
    @NotBlank(message = "Error: El teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9]{10}$", message = "Error: El teléfono debe tener 10 dígitos")
    private String phone;
    
    @NotBlank(message = "Error: La dirección no puede estar vacía")
    @Size(min = 5, max = 200, message = "Error: La dirección debe tener entre 5 y 200 caracteres")
    private String address;
}
