package com.wipro.fraudms.dto;



import lombok.Data;

@Data
public class CheckReq {

    private int transactionId;
    private String fromAccount;
    private String toAccount;
    private double amount;
    private int loginFailureCount;
    private boolean deviceMismatch;
}