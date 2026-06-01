package com.wipro.userms.dto;



import lombok.Data;

@Data
public class Profile {

    private String fullName;
    private String phone;
    private String userName;

    private String oldPassword;
    private String newPassword;

    private double amount;
}