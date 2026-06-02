package com.wipro.fraudms.service;

import java.util.List;

import com.wipro.fraudms.dto.CheckReq;
import com.wipro.fraudms.dto.CheckRes;
import com.wipro.fraudms.entity.Alerts;
import com.wipro.fraudms.entity.Blacklist;
import com.wipro.fraudms.entity.TransRules;

public interface FraudService {

    TransRules saveRule(TransRules rule);

    TransRules getActiveRule();

    List<TransRules> getAllRules();

    Blacklist addBlacklist(Blacklist account);

    List<Blacklist> getBlacklist();

    void deleteBlacklist(int id);

    CheckRes checkFraud(CheckReq request);

    List<Alerts> getAllAlerts();

    List<Alerts> getAlertsByAccount(String accountNumber);
}
