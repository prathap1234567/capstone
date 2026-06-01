package com.wipro.userms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;
    String fullName;
    String phone;
    @Column
    String userEmail;
    @Column
    String userName;
    String userPass;
    String userRole;
    int failedAttempts;
    boolean accountLocked;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="account_id")
    Account account;
}