package com.wipro.fraudms.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class CheckRes {
    private String decision;
    private String reason;
}