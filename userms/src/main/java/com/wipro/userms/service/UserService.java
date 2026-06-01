package com.wipro.userms.service;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.wipro.userms.dto.Profile;
import com.wipro.userms.entity.User;



public interface UserService extends UserDetailsService {

    User register(User u);
    String login(User u);
    List<User>findAll();
    User findById(int id);
    User findByEmail(String email);
    User activateUser(int id);
    User deactivateUser(int id);
    void deleteById(int id);
    
    User updateProfile(int id, Profile p);
    String changePassword(int id, Profile p);
    User findByAccountNumber(String accountNumber);
    User loadMoney(int id, Profile p);
}