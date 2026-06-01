package com.wipro.transactionms.dto;

import lombok.Data;

@Data
public class SendMoney {

    private String fromAccount;

    private String toAccount;

    private double amount;

    private String remarks;
}