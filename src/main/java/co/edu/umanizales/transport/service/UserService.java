package co.edu.umanizales.transport.service;

import co.edu.umanizales.transport.model.Client;
import co.edu.umanizales.transport.model.Employee;
import co.edu.umanizales.transport.model.User;
import co.edu.umanizales.transport.util.CSVUtil;
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
        for (Client client : clients) {
            if (client.getId().equals(id)) {
                return client;
            }
        }
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    public User updateUser(Long id, User user) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId().equals(id)) {
                Client updated = (Client) user;
                updated.setId(id);
                clients.set(i, updated);
                saveClientsToCSV();
                return updated;
            }
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id)) {
                Employee updated = (Employee) user;
                updated.setId(id);
                employees.set(i, updated);
                saveEmployeesToCSV();
                return updated;
            }
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        boolean removed = clients.removeIf(c -> c.getId().equals(id));
        if (removed) {
            saveClientsToCSV();
            return true;
        }
        removed = employees.removeIf(e -> e.getId().equals(id));
        if (removed) {
            saveEmployeesToCSV();
            return true;
        }
        return false;
    }

    private void saveClientsToCSV() {
        List<String> headers = Arrays.asList("id", "name", "email", "phone", "address");
        List<List<String>> rows = clients.stream().map(c -> Arrays.asList(
            c.getId().toString(),
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
            e.getId().toString(),
            e.getName(),
            e.getEmail(),
            e.getEmployeeId(),
            e.getDepartment(),
            e.getSalary().toString()
        )).collect(Collectors.toList());
        CSVUtil.writeToCSV(EMPLOYEE_FILE, headers, rows);
    }
}
