package com.wipro.transactionms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payees")
@Data
public class Payee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payeeId;

    private String userAccountNumber;

    private String payeeName;

    private String payeeAccountNumber;

    private String bankName;
}