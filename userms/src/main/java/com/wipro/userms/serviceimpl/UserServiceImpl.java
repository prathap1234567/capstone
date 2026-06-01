package com.wipro.userms.serviceimpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.wipro.userms.dto.Profile;
import com.wipro.userms.entity.Account;
import com.wipro.userms.entity.User;
import com.wipro.userms.repo.UserRepo;
import com.wipro.userms.service.UserService;
import com.wipro.userms.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public User register(User u){
        u.setUserPass(passwordEncoder.encode(u.getUserPass()));
        u.setUserRole("USER");
        u.setFailedAttempts(0);
        u.setAccountLocked(false);
        Account a= new Account();
        a.setAccountNumber(generateAccountNumber());
        a.setBalance(0);
        a.setAccountStatus("INACTIVE");
        u.setAccount(a);
        return userRepo.save(u);
    }
    private String generateAccountNumber(){
        long n=1000000000L+(long)(Math.random()*9000000000L);
        return "ACC"+n;
    }

    @Override
    public String login(User u){
        User olduser=userRepo.findByUserEmail(u.getUserEmail());
        if(olduser==null){
            return null;
        }
        if(olduser.isAccountLocked()){
            return "LOCKED";
        }
        if(!passwordEncoder.matches(u.getUserPass(),olduser.getUserPass())){
            int count=olduser.getFailedAttempts()+1;
            olduser.setFailedAttempts(count);
            if(count>=3){
                olduser.setAccountLocked(true);
            }
            userRepo.save(olduser);
            return null;
        }
        olduser.setFailedAttempts(0);
        userRepo.save(olduser);
        String token=jwtUtil.generateToken(olduser.getUserEmail());
        return token;
    }
    @Override
    public List<User>findAll() {
        return userRepo.findAll();
    }
    @Override
    public User findById(int id) {
        Optional<User>u= userRepo.findById(id);
        if(u.isPresent()){
            return u.get();
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByUserEmail(email);
    }

    @Override
    public User activateUser(int id){
        User u= findById(id);
        if(u==null){
            return null;
        }
        u.getAccount().setAccountStatus("ACTIVE");
        u.setAccountLocked(false);
        u.setFailedAttempts(0);
        return userRepo.save(u);
    }
    @Override
    public User deactivateUser(int id){
        User u=findById(id);
        if(u==null){
            return null;
        }
        u.getAccount().setAccountStatus("INACTIVE");
        return userRepo.save(u);
    }
    @Override
    public void deleteById(int id){
        userRepo.deleteById(id);
    }
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException{
        User u=userRepo.findByUserEmail(email);
        if(u==null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(u.getUserEmail())
                .password(u.getUserPass())
                .roles(u.getUserRole())
                .build();
    }
    @Override
    public User updateProfile(int id,Profile p) {
        User u=findById(id);
        if(u==null){
            return null;
        }
        u.setFullName(p.getFullName());
        u.setPhone(p.getPhone());
        u.setUserName(p.getUserName());
        return userRepo.save(u);
    }
    @Override
    public String changePassword(int id,Profile p){
        User u=findById(id);
        if(u==null){
            return "User Not Found";
        }
        boolean isCorrect=passwordEncoder.matches(
                p.getOldPassword(),
                u.getUserPass()
        );
        if(!isCorrect){
            return "Password wrong";
        }
        String newPass=passwordEncoder.encode(p.getNewPassword());
        u.setUserPass(newPass);
        userRepo.save(u);
        return "updated";
    }
    @Override
    public User loadMoney(int id,Profile p){
        User u=findById(id);
        if(u== null){
            return null;
        }
        double oldBalance= u.getAccount().getBalance();
        double newBalance= oldBalance+p.getAmount();
        u.getAccount().setBalance(newBalance);
        return userRepo.save(u);
    }
    @Override
    public User findByAccountNumber(String accountNumber) {
        return userRepo.findByAccountAccountNumber(accountNumber);
    }
}