package co.edu.umanizales.transport.service;

import co.edu.umanizales.transport.model.Client;
import co.edu.umanizales.transport.model.Employee;
import co.edu.umanizales.transport.model.User;
import co.edu.umanizales.transport.util.CSVUtil;
import co.edu.umanizales.transport.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final String CLIENT_FILE = "clients.csv";
    private static final String EMPLOYEE_FILE = "employees.csv";
    private List<Client> clients = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private Long nextClientId = 1L;
    private Long nextEmployeeId = 1L;

    public UserService() {
        loadFromCSV();
    }

    private void loadFromCSV() {
        if (CSVUtil.fileExists(CLIENT_FILE)) {
            List<Map<String, String>> data = CSVUtil.readFromCSV(CLIENT_FILE);
            for (Map<String, String> row : data) {
                Client client = new Client();
                client.setId(Long.parseLong(row.get("id")));
                client.setName(row.get("name"));
                client.setEmail(row.get("email"));
                client.setPhone(row.get("phone"));
                client.setAddress(row.get("address"));
                clients.add(client);
                nextClientId = Math.max(nextClientId, client.getId() + 1);
            }
        }

        if (CSVUtil.fileExists(EMPLOYEE_FILE)) {
            List<Map<String, String>> data = CSVUtil.readFromCSV(EMPLOYEE_FILE);
            for (Map<String, String> row : data) {
                Employee employee = new Employee();
                employee.setId(Long.parseLong(row.get("id")));
                employee.setName(row.get("name"));
                employee.setEmail(row.get("email"));
                employee.setEmployeeId(row.get("employeeId"));
                employee.setDepartment(row.get("department"));
                employee.setSalary(Double.parseDouble(row.get("salary")));
                employees.add(employee);
                nextEmployeeId = Math.max(nextEmployeeId, employee.getId() + 1);
            }
        }
    }

    public Client createClient(Client client) {
        client.setId(nextClientId++);
        clients.add(client);
        saveClientsToCSV();
        return client;
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(nextEmployeeId++);
        employees.add(employee);
        saveEmployeesToCSV();
        return employee;
    }

    public List<User> getAllUsers() {
        List<User> all = new ArrayList<>();
        all.addAll(clients);
        all.addAll(employees);
        return all;
    }

    public User getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Error: El ID del usuario debe ser un número válido mayor a 0");
        }
        
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        throw new ResourceNotFoundException("Error: este dato no existe - Usuario con ID " + id + " no encontrado");
    }

    public User updateUser(Long id, User user) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Error: El ID del usuario debe ser un número válido mayor a 0");
        }
        if (user == null) {
            throw new IllegalArgumentException("Error: Los datos del usuario no pueden estar vacíos");
        }
        
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == id) {
                Client updated = (Client) user;
                updated.setId(id);
                clients.set(i, updated);
                saveClientsToCSV();
                return updated;
            }
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                Employee updated = (Employee) user;
                updated.setId(id);
                employees.set(i, updated);
                saveEmployeesToCSV();
                return updated;
            }
        }
        throw new ResourceNotFoundException("Error: este dato no existe - Usuario con ID " + id + " no encontrado para actualizar");
    }

    public boolean deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Error: El ID del usuario debe ser un número válido mayor a 0");
        }
        
        boolean removed = clients.removeIf(c -> c.getId() == id);
        if (removed) {
            saveClientsToCSV();
            return true;
        }
        removed = employees.removeIf(e -> e.getId() == id);
        if (removed) {
            saveEmployeesToCSV();
            return true;
        }
        throw new ResourceNotFoundException("Error: este dato no existe - Usuario con ID " + id + " no encontrado para eliminar");
    }

    private void saveClientsToCSV() {
        List<String> headers = Arrays.asList("id", "name", "email", "phone", "address");
        List<List<String>> rows = clients.stream().map(c -> Arrays.asList(
            String.valueOf(c.getId()),
            c.getName(),
            c.getEmail(),
            c.getPhone(),
            c.getAddress()
        )).collect(Collectors.toList());
        CSVUtil.writeToCSV(CLIENT_FILE, headers, rows);
    }

    private void saveEmployeesToCSV() {
        List<String> headers = Arrays.asList("id", "name", "email", "employeeId", "department", "salary");
        List<List<String>> rows = employees.stream().map(e -> Arrays.asList(
            String.valueOf(e.getId()),
            e.getName(),
            e.getEmail(),
            e.getEmployeeId(),
            e.getDepartment(),
            String.valueOf(e.getSalary())
        )).collect(Collectors.toList());
        CSVUtil.writeToCSV(EMPLOYEE_FILE, headers, rows);
    }
}
