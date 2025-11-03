package co.edu.umanizales.transport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalContract {
    private long id;
    private Vehicle vehicle;
    private Client client;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private String status;
    private PaymentMethod paymentMethod;
}
