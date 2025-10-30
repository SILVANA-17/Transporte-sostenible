package co.edu.umanizales.transport.controller;

import co.edu.umanizales.transport.model.ElectricBicycle;
import co.edu.umanizales.transport.model.ElectricMotorcycle;
import co.edu.umanizales.transport.model.Vehicle;
import co.edu.umanizales.transport.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/bicycles")
    public ResponseEntity<ElectricBicycle> createBicycle(@RequestBody ElectricBicycle bicycle) {
        ElectricBicycle created = vehicleService.createBicycle(bicycle);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/motorcycles")
    public ResponseEntity<ElectricMotorcycle> createMotorcycle(@RequestBody ElectricMotorcycle motorcycle) {
        ElectricMotorcycle created = vehicleService.createMotorcycle(motorcycle);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        Vehicle updated = vehicleService.updateVehicle(id, vehicle);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (vehicleService.deleteVehicle(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
