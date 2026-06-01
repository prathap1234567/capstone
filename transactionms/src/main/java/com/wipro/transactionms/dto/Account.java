package com.wipro.transactionms.dto;

import lombok.Data;

@Data
public class Account {

    private int accountId;

    private String accountNumber;

    private double balance;

    private String accountStatus;
}