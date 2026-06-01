package com.wipro.userms.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.wipro.userms.dto.LoginResponse;
import com.wipro.userms.dto.Profile;
import com.wipro.userms.entity.User;
import com.wipro.userms.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/register")
    ResponseEntity<String>register(@RequestBody User u){
        User oldUser= userService.findByEmail(u.getUserEmail());

        if(oldUser!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User exists");
        }
        User saved=userService.register(u);
        if(saved!=null){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Register Success");
        }
        return ResponseEntity
                 .status(HttpStatus.BAD_REQUEST)
                .body("Register Failed");
    }
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(
            @RequestBody User u,
            HttpServletResponse response){
        String token=userService.login(u);
        if("LOCKED".equals(token)){
            return ResponseEntity
                    .status(HttpStatus.LOCKED)
                    .body(new LoginResponse(null,null,"FAILED","Account Locked"));
        }
        if(token== null){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null,null,"FAILED","Invalid Email or Password"));
        }
        User loggedUser=
                userService.findByEmail(u.getUserEmail());
        Cookie cookie=new Cookie("jwt",token);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24*60*60);
        response.addCookie(cookie);
        return ResponseEntity.ok(new LoginResponse(loggedUser.getId(),loggedUser.getUserRole(),"SUCCESS","Login Successful"));
    }

    @GetMapping("/profile/{id}")
    ResponseEntity<User> getProfile(
            @PathVariable int id){
                   User u= userService.findById(id);

        if(u==null){
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(u);
    }
    @GetMapping("/admin/users")
    ResponseEntity<List<User>>getAllUsers(){
        return ResponseEntity.ok(
                userService.findAll()
        );
    }
    @PutMapping("/admin/activate/{id}")
    ResponseEntity<User>activateUser(@PathVariable int id){
        User u=userService.activateUser(id);
        if(u==null){ 
        	return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(u);
    }
    @PutMapping("/admin/deactivate/{id}")
    ResponseEntity<User> deactivateUser(
            @PathVariable int id){
                 User u=userService.deactivateUser(id);
             if(u==null){
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(u);
    }
    @DeleteMapping("/admin/delete/{id}")
    ResponseEntity<String> deleteUser(
            @PathVariable int id){
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }
    @PutMapping("/profile/{id}")
    ResponseEntity<User>updateProfile(
            @PathVariable int id,
            @RequestBody Profile p){
        User u= userService.updateProfile(id,p);
              
        if(u==null){
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(u);
    }
    @PutMapping("/change-password/{id}")
    ResponseEntity<String> changePassword(
            @PathVariable int id,
            @RequestBody Profile p){
        String res=userService.changePassword( id,p);
        if(res.equals("OLD_PASSWORD_WRONG")||res.equals("Password wrong")){
            return ResponseEntity
                    .badRequest()
                    .body("Old Password Wrong");
        }
        return ResponseEntity.ok("Password Changed");
    }
    @PutMapping("/load-money/{id}")
    ResponseEntity<User>loadMoney(
            @PathVariable int id,
            @RequestBody Profile p){

        User u=userService.loadMoney(id,p);

        if(u== null){
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(u);
    }
    @GetMapping("/account/{accountNumber}")
    ResponseEntity<User> getUserByAccountNumber(
            @PathVariable String accountNumber){
        User u=userService.findByAccountNumber( accountNumber);

        if(u==null){
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(u);
    }
}