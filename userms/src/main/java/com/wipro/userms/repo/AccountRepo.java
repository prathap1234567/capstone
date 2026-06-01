package com.wipro.userms.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.userms.entity.Account;


@Repository
public interface AccountRepo extends JpaRepository<Account,Integer> {

}
