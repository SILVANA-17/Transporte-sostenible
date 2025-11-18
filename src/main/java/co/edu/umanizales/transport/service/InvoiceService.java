package co.edu.umanizales.transport.service;

import co.edu.umanizales.transport.model.Invoice;
import co.edu.umanizales.transport.util.CSVUtil;
import co.edu.umanizales.transport.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class InvoiceService {
    private static final String INVOICE_FILE = "invoices.csv";
    private List<Invoice> invoices = new ArrayList<>();
    private Long nextInvoiceId = 1L;

    public InvoiceService() {
        loadFromCSV();
    }

    private void loadFromCSV() {
        if (CSVUtil.fileExists(INVOICE_FILE)) {
            List<Map<String, String>> data = CSVUtil.readFromCSV(INVOICE_FILE);
            for (Map<String, String> row : data) {
                try {
                    String invoiceId = row.get("invoiceId");
                    String date = row.get("date");
                    String totalValue = row.get("totalValue");
                    
                    if ((invoiceId == null || invoiceId.equals("null") || invoiceId.isEmpty()) ||
                        (date == null || date.equals("null") || date.isEmpty()) ||
                        (totalValue == null || totalValue.equals("null") || totalValue.isEmpty())) {
                        continue;
                    }
                    
                    Invoice invoice = new Invoice(
                        Long.parseLong(invoiceId),
                        LocalDate.parse(date),
                        Double.parseDouble(totalValue)
                    );
                    invoices.add(invoice);
                    nextInvoiceId = Math.max(nextInvoiceId, invoice.invoiceId() + 1);
                } catch (Exception e) {
                    System.err.println("Error al cargar factura: " + e.getMessage());
                }
            }
        }
    }

    public Invoice createInvoice(Invoice invoice) {
        Invoice newInvoice = new Invoice(nextInvoiceId++, invoice.date(), invoice.totalValue());
        invoices.add(newInvoice);
        saveInvoicesToCSV();
        return newInvoice;
    }

    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoices);
    }

    public Invoice getInvoiceById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Error: El ID de la factura debe ser un número válido mayor a 0");
        }
        
        for (Invoice i : invoices) {
            if (i.invoiceId().equals(id)) {
                return i;
            }
        }
        throw new ResourceNotFoundException("Error: este dato no existe - Factura con ID " + id + " no encontrada");
    }

    public boolean deleteInvoice(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Error: El ID de la factura debe ser un número válido mayor a 0");
        }
        
        boolean removed = false;
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).invoiceId().equals(id)) {
                invoices.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            saveInvoicesToCSV();
            return true;
        }
        throw new ResourceNotFoundException("Error: este dato no existe - Factura con ID " + id + " no encontrada para eliminar");
    }

    private void saveInvoicesToCSV() {
        List<String> headers = Arrays.asList("invoiceId", "date", "totalValue");
        List<List<String>> rows = new ArrayList<>();
        for (Invoice i : invoices) {
            List<String> row = new ArrayList<>();
            row.add(i.invoiceId().toString());
            row.add(i.date().toString());
            row.add(i.totalValue().toString());
            rows.add(row);
        }
        CSVUtil.writeToCSV(INVOICE_FILE, headers, rows);
    }
}
