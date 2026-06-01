package com.wipro.transactionms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.transactionms.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByFromAccountOrToAccount(
            String fromAccount,
            String toAccount
    );
}