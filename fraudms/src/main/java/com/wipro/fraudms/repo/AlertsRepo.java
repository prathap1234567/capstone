package com.wipro.fraudms.repo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.fraudms.entity.Alerts;


public interface AlertsRepo extends JpaRepository<Alerts,Integer>{
    List<Alerts> findByFromAccount(String fromAccount);
}