package com.wipro.transactionms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.transactionms.entity.Payee;

@Repository
public interface PayeeRepo extends JpaRepository<Payee, Integer> {

    List<Payee> findByUserAccountNumber(String userAccountNumber);
}