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
public class ElectricBicycle extends Vehicle implements Maintenance {
    @Positive(message = "Error: La capacidad de batería debe ser mayor a 0")
    private double batteryCapacity;
    
    private String lastMaintenanceDate;

    @Override
    public void performMaintenance() {
        this.lastMaintenanceDate = java.time.LocalDate.now().toString();
    }

    @Override
    public String getNextMaintenanceDate() {
        if (lastMaintenanceDate == null) {
            return "No maintenance record";
        }
        return java.time.LocalDate.parse(lastMaintenanceDate).plusMonths(3).toString();
    }
}
