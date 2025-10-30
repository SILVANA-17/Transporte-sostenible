package co.edu.umanizales.transport.service;

import co.edu.umanizales.transport.model.PaymentMethod;
import co.edu.umanizales.transport.model.RentalContract;
import co.edu.umanizales.transport.util.CSVUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RentalContractService {
    private static final String CONTRACT_FILE = "rental_contracts.csv";
    private List<RentalContract> contracts = new ArrayList<>();
    private Long nextContractId = 1L;

    public RentalContractService() {
        loadFromCSV();
    }

    private void loadFromCSV() {
        if (CSVUtil.fileExists(CONTRACT_FILE)) {
            List<Map<String, String>> data = CSVUtil.readFromCSV(CONTRACT_FILE);
            for (Map<String, String> row : data) {
                RentalContract contract = new RentalContract();
                contract.setId(Long.parseLong(row.get("id")));
                contract.setVehicleId(Long.parseLong(row.get("vehicleId")));
                contract.setClientId(Long.parseLong(row.get("clientId")));
                contract.setStartDate(LocalDate.parse(row.get("startDate")));
                contract.setEndDate(LocalDate.parse(row.get("endDate")));
                contract.setTotalPrice(Double.parseDouble(row.get("totalPrice")));
                contract.setStatus(row.get("status"));
                contract.setPaymentMethod(PaymentMethod.valueOf(row.get("paymentMethod")));
                contracts.add(contract);
                nextContractId = Math.max(nextContractId, contract.getId() + 1);
            }
        }
    }

    public RentalContract createContract(RentalContract contract) {
        contract.setId(nextContractId++);
        contracts.add(contract);
        saveContractsToCSV();
        return contract;
    }

    public List<RentalContract> getAllContracts() {
        return new ArrayList<>(contracts);
    }

    public RentalContract getContractById(Long id) {
        return contracts.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public RentalContract updateContract(Long id, RentalContract contract) {
        for (int i = 0; i < contracts.size(); i++) {
            if (contracts.get(i).getId().equals(id)) {
                contract.setId(id);
                contracts.set(i, contract);
                saveContractsToCSV();
                return contract;
            }
        }
        return null;
    }

    public boolean deleteContract(Long id) {
        boolean removed = contracts.removeIf(c -> c.getId().equals(id));
        if (removed) {
            saveContractsToCSV();
        }
        return removed;
    }

    private void saveContractsToCSV() {
        List<String> headers = Arrays.asList("id", "vehicleId", "clientId", "startDate", "endDate", "totalPrice", "status", "paymentMethod");
        List<List<String>> rows = contracts.stream().map(c -> Arrays.asList(
            c.getId().toString(),
            c.getVehicleId().toString(),
            c.getClientId().toString(),
            c.getStartDate().toString(),
            c.getEndDate().toString(),
            c.getTotalPrice().toString(),
            c.getStatus(),
            c.getPaymentMethod().toString()
        )).collect(Collectors.toList());
        CSVUtil.writeToCSV(CONTRACT_FILE, headers, rows);
    }
}
