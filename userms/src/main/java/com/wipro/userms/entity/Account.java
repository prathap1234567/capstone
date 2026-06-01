package com.wipro.userms.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int accountId;
    @Column
    String accountNumber;
    double balance;
    String accountStatus;
}
