package com.wipro.transactionms.dto;

import lombok.Data;

@Data
public class User {

    private int id;

    private String fullName;

    private String phone;

    private String userEmail;

    private String userName;

    private String userRole;

    private Account account;
}