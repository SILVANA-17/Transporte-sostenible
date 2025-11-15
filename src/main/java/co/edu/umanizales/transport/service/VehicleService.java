package co.edu.umanizales.transport.service;

import co.edu.umanizales.transport.model.ElectricBicycle;
import co.edu.umanizales.transport.model.ElectricMotorcycle;
import co.edu.umanizales.transport.model.Vehicle;
import co.edu.umanizales.transport.util.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private static final String BICYCLE_FILE = "electric_bicycles.csv";
    private static final String MOTORCYCLE_FILE = "electric_motorcycles.csv";
    private List<ElectricBicycle> bicycles = new ArrayList<>();
    private List<ElectricMotorcycle> motorcycles = new ArrayList<>();
    private Long nextBicycleId = 1L;
    private Long nextMotorcycleId = 1L;

    public VehicleService() {
        loadFromCSV();
    }

    private void loadFromCSV() {
        if (CSVUtil.fileExists(BICYCLE_FILE)) {
            List<Map<String, String>> data = CSVUtil.readFromCSV(BICYCLE_FILE);
            for (Map<String, String> row : data) {
                ElectricBicycle bike = new ElectricBicycle();
                bike.setId(Long.parseLong(row.get("id")));
                bike.setBrand(row.get("brand"));
                bike.setModel(row.get("model"));
                bike.setPricePerHour(Double.parseDouble(row.get("pricePerHour")));
                bike.setBatteryCapacity(Double.parseDouble(row.get("batteryCapacity")));
                bike.setLastMaintenanceDate(row.get("lastMaintenanceDate"));
                bicycles.add(bike);
                nextBicycleId = Math.max(nextBicycleId, bike.getId() + 1);
            }
        }

        if (CSVUtil.fileExists(MOTORCYCLE_FILE)) {
            List<Map<String, String>> data = CSVUtil.readFromCSV(MOTORCYCLE_FILE);
            for (Map<String, String> row : data) {
                ElectricMotorcycle moto = new ElectricMotorcycle();
                moto.setId(Long.parseLong(row.get("id")));
                moto.setBrand(row.get("brand"));
                moto.setModel(row.get("model"));
                moto.setPricePerHour(Double.parseDouble(row.get("pricePerHour")));
                moto.setBatteryCapacity(Double.parseDouble(row.get("batteryCapacity")));
                moto.setMaxSpeed(Integer.parseInt(row.get("maxSpeed")));
                moto.setLastMaintenanceDate(row.get("lastMaintenanceDate"));
                motorcycles.add(moto);
                nextMotorcycleId = Math.max(nextMotorcycleId, moto.getId() + 1);
            }
        }
    }

    public ElectricBicycle createBicycle(ElectricBicycle bicycle) {
        bicycle.setId(nextBicycleId++);
        bicycles.add(bicycle);
        saveBicyclesToCSV();
        return bicycle;
    }

    public ElectricMotorcycle createMotorcycle(ElectricMotorcycle motorcycle) {
        motorcycle.setId(nextMotorcycleId++);
        motorcycles.add(motorcycle);
        saveMotorcyclesToCSV();
        return motorcycle;
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> all = new ArrayList<>();
        all.addAll(bicycles);
        all.addAll(motorcycles);
        return all;
    }

    public Vehicle getVehicleById(Long id) {
        for (ElectricBicycle bike : bicycles) {
            if (bike.getId() == id) {
                return bike;
            }
        }
        for (ElectricMotorcycle moto : motorcycles) {
            if (moto.getId() == id) {
                return moto;
            }
        }
        return null;
    }

    public Vehicle updateVehicle(Long id, Vehicle vehicle) {
        for (int i = 0; i < bicycles.size(); i++) {
            if (bicycles.get(i).getId() == id) {
                ElectricBicycle updated = (ElectricBicycle) vehicle;
                updated.setId(id);
                bicycles.set(i, updated);
                saveBicyclesToCSV();
                return updated;
            }
        }
        for (int i = 0; i < motorcycles.size(); i++) {
            if (motorcycles.get(i).getId() == id) {
                ElectricMotorcycle updated = (ElectricMotorcycle) vehicle;
                updated.setId(id);
                motorcycles.set(i, updated);
                saveMotorcyclesToCSV();
                return updated;
            }
        }
        return null;
    }

    public boolean deleteVehicle(Long id) {
        boolean removed = bicycles.removeIf(b -> b.getId() == id);
        if (removed) {
            saveBicyclesToCSV();
            return true;
        }
        removed = motorcycles.removeIf(m -> m.getId() == id);
        if (removed) {
            saveMotorcyclesToCSV();
            return true;
        }
        return false;
    }

    private void saveBicyclesToCSV() {
        List<String> headers = Arrays.asList("id", "brand", "model", "pricePerHour", "batteryCapacity", "lastMaintenanceDate");
        List<List<String>> rows = bicycles.stream().map(b -> Arrays.asList(
            String.valueOf(b.getId()),
            b.getBrand(),
            b.getModel(),
            String.valueOf(b.getPricePerHour()),
            String.valueOf(b.getBatteryCapacity()),
            b.getLastMaintenanceDate() != null ? b.getLastMaintenanceDate() : ""
        )).collect(Collectors.toList());
        CSVUtil.writeToCSV(BICYCLE_FILE, headers, rows);
    }

    private void saveMotorcyclesToCSV() {
        List<String> headers = Arrays.asList("id", "brand", "model", "pricePerHour", "batteryCapacity", "maxSpeed", "lastMaintenanceDate");
        List<List<String>> rows = motorcycles.stream().map(m -> Arrays.asList(
            String.valueOf(m.getId()),
            m.getBrand(),
            m.getModel(),
            String.valueOf(m.getPricePerHour()),
            String.valueOf(m.getBatteryCapacity()),
            String.valueOf(m.getMaxSpeed()),
            m.getLastMaintenanceDate() != null ? m.getLastMaintenanceDate() : ""
        )).collect(Collectors.toList());
        CSVUtil.writeToCSV(MOTORCYCLE_FILE, headers, rows);
    }
}
