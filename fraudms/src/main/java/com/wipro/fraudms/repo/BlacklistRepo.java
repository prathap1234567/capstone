package com.wipro.fraudms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.fraudms.entity.Blacklist;


public interface BlacklistRepo  extends JpaRepository<Blacklist, Integer> {

    Blacklist findByAccountNumber(String accountNumber);
}