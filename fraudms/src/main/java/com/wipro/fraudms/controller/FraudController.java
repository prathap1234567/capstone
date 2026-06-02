package com.wipro.fraudms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.wipro.fraudms.dto.CheckReq;
import com.wipro.fraudms.dto.CheckRes;
import com.wipro.fraudms.entity.Alerts;
import com.wipro.fraudms.entity.Blacklist;
import com.wipro.fraudms.entity.TransRules;
import com.wipro.fraudms.service.FraudService;

@RestController
@RequestMapping("/fraud")
@CrossOrigin(origins="http://localhost:4200",allowCredentials="true")
public class FraudController {

    @Autowired
    FraudService service;

    @PostMapping("/rules")
    ResponseEntity<TransRules>saveRule(@RequestBody TransRules r){

        TransRules saved=service.saveRule(r);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping("/rules/active")
    ResponseEntity<TransRules>getActiveRule(){

        TransRules r=service.getActiveRule();

        if(r==null){
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(r);
    }

    @GetMapping("/rules")
    ResponseEntity<List<TransRules>>getAllRules(){

        return ResponseEntity.ok(
                service.getAllRules()
        );
    }

    @PostMapping("/blacklist")
    ResponseEntity<Blacklist>addBlacklist(@RequestBody Blacklist b){

        Blacklist saved=service.addBlacklist(b);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping("/blacklist")
    ResponseEntity<List<Blacklist>>getBlacklist(){

        return ResponseEntity.ok(
                service.getBlacklist()
        );
    }

    @DeleteMapping("/blacklist/{id}")
    ResponseEntity<String>deleteBlacklist(@PathVariable int id){

        service.deleteBlacklist(id);

        return ResponseEntity.ok(
                "Blacklisted account deleted successfully");
    }

    @PostMapping("/check")
    ResponseEntity<?>checkFraud(@RequestBody CheckReq req){

        try{

            CheckRes res=service.checkFraud(req);

            return ResponseEntity.ok(res);

        }catch(Exception e){

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/alerts")
    ResponseEntity<List<Alerts>>getAllAlerts(){

        return ResponseEntity.ok(
                service.getAllAlerts()
        );
    }

    @GetMapping("/alerts/account/{accountNumber}")
    ResponseEntity<List<Alerts>>getAlertsByAccount(
            @PathVariable String accountNumber){

        return ResponseEntity.ok(
                service.getAlertsByAccount(accountNumber)
        );
    }
}