package co.edu.umanizales.transport.controller;

import co.edu.umanizales.transport.model.RentalContract;
import co.edu.umanizales.transport.service.RentalContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/rental-contracts")
public class RentalContractController {
    @Autowired
    private RentalContractService rentalContractService;

    @PostMapping
    public ResponseEntity<RentalContract> createContract(@Valid @RequestBody RentalContract contract) {
        RentalContract created = rentalContractService.createContract(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<RentalContract>> getAllContracts() {
        List<RentalContract> contracts = rentalContractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalContract> getContractById(@PathVariable Long id) {
        RentalContract contract = rentalContractService.getContractById(id);
        return ResponseEntity.ok(contract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalContract> updateContract(@PathVariable Long id, @Valid @RequestBody RentalContract contract) {
        RentalContract updated = rentalContractService.updateContract(id, contract);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        rentalContractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}
