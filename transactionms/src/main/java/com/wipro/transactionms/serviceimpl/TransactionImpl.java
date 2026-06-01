package com.wipro.transactionms.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.transactionms.config.UserConnect;
import com.wipro.transactionms.dto.SendMoney;
import com.wipro.transactionms.dto.User;
import com.wipro.transactionms.entity.Payee;
import com.wipro.transactionms.entity.Transaction;
import com.wipro.transactionms.repo.PayeeRepo;
import com.wipro.transactionms.repo.TransactionRepo;
import com.wipro.transactionms.service.TransactionService;

@Service
public class TransactionImpl implements TransactionService {

    @Autowired
    PayeeRepo payeeRepo;

    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    UserConnect userConnect;

    @Override
    public Payee addPayee(Payee payee) {
    	User user =
    	        userConnect.getUserByAccountNumber(
    	                payee.getPayeeAccountNumber());

    	if(user == null) {
    	    throw new RuntimeException(
    	            "Payee Account Number Not Found");
    	}
        return payeeRepo.save(payee);
        
        
        
    }

    @Override
    public List<Payee> getPayees(String accountNumber) {
        return payeeRepo.findByUserAccountNumber(accountNumber);
    }

    @Override
    public void deletePayee(int id) {
        payeeRepo.deleteById(id);
    }

    @Override
    public Transaction transferMoney(SendMoney send) {

        User fromUser =
                userConnect.getUserByAccountNumber(
                        send.getFromAccount());

        User toUser =
                userConnect.getUserByAccountNumber(
                        send.getToAccount());

        if (fromUser == null || fromUser.getAccount() == null) {
            throw new RuntimeException("Sender account not found");
        }

        if (toUser == null || toUser.getAccount() == null) {
            throw new RuntimeException("Receiver account not found");
        }

        if (!"ACTIVE".equals(fromUser.getAccount().getAccountStatus())) {
            throw new RuntimeException("Sender account is not active");
        }

        if (!"ACTIVE".equals(toUser.getAccount().getAccountStatus())) {
            throw new RuntimeException("Receiver account is not active");
        }

        if (fromUser.getAccount().getBalance() < send.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        Transaction transaction = new Transaction();

        transaction.setFromAccount(send.getFromAccount());
        transaction.setToAccount(send.getToAccount());
        transaction.setAmount(send.getAmount());
        transaction.setRemarks(send.getRemarks());
        transaction.setTransactionType("TRANSFER");
        transaction.setStatus("PENDING");
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepo.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountNumber) {

        return transactionRepo.findByFromAccountOrToAccount(
                accountNumber,
                accountNumber
        );
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    @Override
    public Transaction findById(int id) {

        Optional<Transaction> opt =
                transactionRepo.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        }

        return null;
    }

    @Override
    public Transaction updateStatus(int id, String status) {

        Transaction transaction = findById(id);

        if (transaction == null) {
            return null;
        }

        transaction.setStatus(status);

        return transactionRepo.save(transaction);
    }
}