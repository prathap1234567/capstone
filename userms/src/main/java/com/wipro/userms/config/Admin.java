package com.wipro.userms.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.wipro.userms.entity.User;
import com.wipro.userms.repo.UserRepo;

@Component
public class Admin implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        User admin=userRepo.findByUserEmail("admin@gmail.com");
        if(admin== null){
            User user=new User();
            user.setFullName("System Admin");
            user.setPhone("9875765478");
            user.setUserEmail("admin@gmail.com");
            user.setUserName("admin");
            user.setUserPass(passwordEncoder.encode("admin@123") );
            user.setUserRole("ADMIN");
            user.setFailedAttempts(0);
            user.setAccountLocked(false);
            user.setAccount(null);
            userRepo.save(user);
        }
    }
}