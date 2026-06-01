package com.wipro.userms.dto;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}