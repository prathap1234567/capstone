package com.wipro.fraudms.Config;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wipro.fraudms.dto.TransactionDto;


@FeignClient(
        name = "transaction-ms",
        url = "http://localhost:8082/transactions"
)
public interface FeignConfig {

    @GetMapping("/account/{accountNumber}")
    List<TransactionDto> getTransactionsByAccount(
            @PathVariable String accountNumber);
}