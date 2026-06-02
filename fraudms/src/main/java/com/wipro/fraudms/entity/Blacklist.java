package com.wipro.fraudms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="blacklisted_accounts")
@Data
public class Blacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String accountNumber;
    private String reason;
}