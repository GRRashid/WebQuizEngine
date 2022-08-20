package engine.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    UserRepository users;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public void registration(@Valid @RequestBody User user){
        for (User u: users.findAll()) {
            if(u.getEmail().equals(user.getEmail())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        user.setPassword(encoder.encode(user.getPassword()));
        users.save(user);
    }

    @GetMapping("/api/req")
    public java.util.List<User> fg(){
        return users.findAll();
    }

    @PostMapping("/actuator/shutdown")
    public void stopping(){

    }


}
