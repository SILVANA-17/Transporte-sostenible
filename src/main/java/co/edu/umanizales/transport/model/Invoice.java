package co.edu.umanizales.transport.model;

import java.time.LocalDate;

public record Invoice(
    Long invoiceId,
    LocalDate date,
    Double totalValue
) {}
