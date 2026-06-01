package com.wipro.transactionms.service;

import java.util.List;

import com.wipro.transactionms.dto.SendMoney;

import com.wipro.transactionms.entity.Payee;
import com.wipro.transactionms.entity.Transaction;

public interface TransactionService {

    Payee addPayee(Payee payee);

    List<Payee> getPayees(String accountNumber);

    void deletePayee(int id);

    Transaction transferMoney(SendMoney send);

    List<Transaction> getTransactionsByAccount(String accountNumber);

    List<Transaction> getAllTransactions();

    Transaction findById(int id);

    Transaction updateStatus(int id, String status);
}