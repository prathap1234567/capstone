package com.wipro.fraudms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="rules")
@Data
public class TransRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ruleId;
    private double threshold;
    private double dailyLimit;
    private int transfersPerMinute;
    private double velocityLimit;
    private int velocityTime;
    private int loginFailureChk;
    private boolean deviceMismatch;
    private boolean active;
}