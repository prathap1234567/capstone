package com.wipro.transactionms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.wipro.transactionms.dto.SendMoney;

import com.wipro.transactionms.entity.Payee;
import com.wipro.transactionms.entity.Transaction;
import com.wipro.transactionms.service.TransactionService;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TransactionController {

    @Autowired
    TransactionService service;

    @PostMapping("/payee/add")
    ResponseEntity<Payee> addPayee(@RequestBody Payee payee) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addPayee(payee));
    }

    @GetMapping("/payee/{accountNumber}")
    ResponseEntity<List<Payee>> getPayees(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                service.getPayees(accountNumber)
        );
    }

    @DeleteMapping("/payee/delete/{id}")
    ResponseEntity<String> deletePayee(@PathVariable int id) {

        service.deletePayee(id);

        return ResponseEntity.ok("Payee deleted successfully");
    }

    @PostMapping("/transfer")
    ResponseEntity<?> transferMoney(
            @RequestBody SendMoney send) {

        if (send.getAmount() <= 0) {
            return ResponseEntity
                    .badRequest()
                    .body("Amount must be greater than zero");
        }

        try {
            Transaction transaction =
                    service.transferMoney(send);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(transaction);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @GetMapping("/account/{accountNumber}")
    ResponseEntity<List<Transaction>> getTransactionsByAccount(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                service.getTransactionsByAccount(accountNumber)
        );
    }

    @GetMapping("/all")
    ResponseEntity<List<Transaction>> getAllTransactions() {

        return ResponseEntity.ok(service.getAllTransactions());
    }

    @GetMapping("/{id}")
    ResponseEntity<Transaction> getTransactionById(
            @PathVariable int id) {

        Transaction transaction = service.findById(id);

        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/status/{id}")
    ResponseEntity<Transaction> updateStatus(
            @PathVariable int id,
            @RequestBody Map<String, String> data) {

        Transaction transaction =
                service.updateStatus(id, data.get("status"));

        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transaction);
    }
}