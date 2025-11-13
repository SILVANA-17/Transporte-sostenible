package co.edu.umanizales.transport.service;

import co.edu.umanizales.transport.model.ElectricBicycle;
import co.edu.umanizales.transport.model.ElectricMotorcycle;
import co.edu.umanizales.transport.model.Vehicle;
import co.edu.umanizales.transport.util.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.*;

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
                String maxSpeedStr = row.get("maxSpeed");
                if (maxSpeedStr != null && !maxSpeedStr.isEmpty()) {
                    moto.setMaxSpeed(Integer.parseInt(maxSpeedStr));
                } else {
                    moto.setMaxSpeed(null);
                }
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
        boolean removed = false;
        for (int i = 0; i < bicycles.size(); i++) {
            if (bicycles.get(i).getId() == id) {
                bicycles.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            saveBicyclesToCSV();
            return true;
        }
        for (int i = 0; i < motorcycles.size(); i++) {
            if (motorcycles.get(i).getId() == id) {
                motorcycles.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            saveMotorcyclesToCSV();
            return true;
        }
        return false;
    }

    private void saveBicyclesToCSV() {
        List<String> headers = Arrays.asList("id", "brand", "model", "pricePerHour", "batteryCapacity", "lastMaintenanceDate");
        List<List<String>> rows = new ArrayList<>();
        for (ElectricBicycle b : bicycles) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(b.getId()));
            row.add(b.getBrand());
            row.add(b.getModel());
            row.add(String.valueOf(b.getPricePerHour()));
            row.add(String.valueOf(b.getBatteryCapacity()));
            row.add(b.getLastMaintenanceDate() != null ? b.getLastMaintenanceDate() : "");
            rows.add(row);
        }
        CSVUtil.writeToCSV(BICYCLE_FILE, headers, rows);
    }

    private void saveMotorcyclesToCSV() {
        List<String> headers = Arrays.asList("id", "brand", "model", "pricePerHour", "batteryCapacity", "maxSpeed", "lastMaintenanceDate");
        List<List<String>> rows = new ArrayList<>();
        for (ElectricMotorcycle m : motorcycles) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(m.getId()));
            row.add(m.getBrand());
            row.add(m.getModel());
            row.add(String.valueOf(m.getPricePerHour()));
            row.add(String.valueOf(m.getBatteryCapacity()));
            row.add(m.getMaxSpeed() != null ? m.getMaxSpeed().toString() : "");
            row.add(m.getLastMaintenanceDate() != null ? m.getLastMaintenanceDate() : "");
            rows.add(row);
        }
        CSVUtil.writeToCSV(MOTORCYCLE_FILE, headers, rows);
    }
}
