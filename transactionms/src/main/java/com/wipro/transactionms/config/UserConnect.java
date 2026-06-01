package com.wipro.transactionms.config;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.wipro.transactionms.dto.User;


@FeignClient(
        name = "user-ms",
        url = "http://localhost:8081/user"
)
public interface UserConnect {

    @GetMapping("/account/{accountNumber}")
    User getUserByAccountNumber(
            @PathVariable String accountNumber);
}