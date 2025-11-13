package co.edu.umanizales.transport.service;

import co.edu.umanizales.transport.model.PaymentMethod;
import co.edu.umanizales.transport.model.RentalContract;
import co.edu.umanizales.transport.model.Vehicle;
import co.edu.umanizales.transport.model.Client;
import co.edu.umanizales.transport.util.CSVUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import jakarta.annotation.PostConstruct;

@Service
public class RentalContractService {
    private static final String CONTRACT_FILE = "rental_contracts.csv";
    private List<RentalContract> contracts = new ArrayList<>();
    private Long nextContractId = 1L;
    private final VehicleService vehicleService;
    private final UserService userService;

    public RentalContractService(VehicleService vehicleService, UserService userService) {
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    @PostConstruct
    private void init() {
        loadFromCSV();
    }

    private void loadFromCSV() {
        if (CSVUtil.fileExists(CONTRACT_FILE)) {
            List<Map<String, String>> data = CSVUtil.readFromCSV(CONTRACT_FILE);
            for (Map<String, String> row : data) {
                RentalContract contract = new RentalContract();
                long id = Long.parseLong(row.get("id"));
                long vehicleId = Long.parseLong(row.get("vehicleId"));
                long clientId = Long.parseLong(row.get("clientId"));
                contract.setId(id);
                Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
                contract.setVehicle(vehicle);
                co.edu.umanizales.transport.model.User user = userService.getUserById(clientId);
                if (user instanceof Client) {
                    contract.setClient((Client) user);
                } else {
                    contract.setClient(null);
                }
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
        for (RentalContract c : contracts) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public RentalContract updateContract(Long id, RentalContract contract) {
        for (int i = 0; i < contracts.size(); i++) {
            if (contracts.get(i).getId() == id) {
                contract.setId(id);
                contracts.set(i, contract);
                saveContractsToCSV();
                return contract;
            }
        }
        return null;
    }

    public boolean deleteContract(Long id) {
        boolean removed = false;
        for (int i = 0; i < contracts.size(); i++) {
            if (contracts.get(i).getId() == id) {
                contracts.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            saveContractsToCSV();
        }
        return removed;
    }

    private void saveContractsToCSV() {
        List<String> headers = Arrays.asList("id", "vehicleId", "clientId", "startDate", "endDate", "totalPrice", "status", "paymentMethod");
        List<List<String>> rows = new ArrayList<>();
        for (RentalContract c : contracts) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(c.getId()));
            row.add(c.getVehicle() != null ? String.valueOf(c.getVehicle().getId()) : "");
            row.add(c.getClient() != null ? String.valueOf(c.getClient().getId()) : "");
            row.add(c.getStartDate() != null ? c.getStartDate().toString() : "");
            row.add(c.getEndDate() != null ? c.getEndDate().toString() : "");
            row.add(String.valueOf(c.getTotalPrice()));
            row.add(c.getStatus() != null ? c.getStatus() : "");
            row.add(c.getPaymentMethod() != null ? c.getPaymentMethod().toString() : "");
            rows.add(row);
        }
        CSVUtil.writeToCSV(CONTRACT_FILE, headers, rows);
    }
}
