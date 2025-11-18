package co.edu.umanizales.transport.controller;

import co.edu.umanizales.transport.model.ElectricBicycle;
import co.edu.umanizales.transport.model.ElectricMotorcycle;
import co.edu.umanizales.transport.model.Vehicle;
import co.edu.umanizales.transport.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/bicycles")
    public ResponseEntity<ElectricBicycle> createBicycle(@Valid @RequestBody ElectricBicycle bicycle) {
        ElectricBicycle created = vehicleService.createBicycle(bicycle);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/motorcycles")
    public ResponseEntity<ElectricMotorcycle> createMotorcycle(@Valid @RequestBody ElectricMotorcycle motorcycle) {
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
        return ResponseEntity.ok(vehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
        Vehicle updated = vehicleService.updateVehicle(id, vehicle);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
