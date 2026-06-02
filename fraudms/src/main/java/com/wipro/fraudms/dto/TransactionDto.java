package com.wipro.fraudms.dto;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionDto {

    private int transactionId;

    private String fromAccount;

    private String toAccount;

    private double amount;

    private String status;

    private LocalDateTime createdAt;
}
