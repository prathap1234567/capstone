package com.wipro.fraudms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="alerts")
@Data
public class Alerts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int alertId;
    private int transactionId;
    private String fromAccount;
    private String toAccount;
    private double amount;
    private String reason;
    private String approval; 
    private LocalDateTime createdOn;
}