package com.wipro.fraudms.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.fraudms.entity.TransRules;

public interface TransRulesRepo extends JpaRepository<TransRules, Integer> {

    TransRules findByActiveTrue();
}