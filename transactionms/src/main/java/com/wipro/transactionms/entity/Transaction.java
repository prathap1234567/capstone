package com.wipro.transactionms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    private String fromAccount;

    private String toAccount;

    private double amount;

    private String remarks;

    private String transactionType; 

    private String status; 

    private LocalDateTime createdAt;
}