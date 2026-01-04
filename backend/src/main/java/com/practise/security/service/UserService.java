package com.practise.security.service;

import com.practise.security.DTO.RaiseIssuedto;
import com.practise.security.Repo.UserRepo;
import com.practise.security.model.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authmanager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);

    public ResponseEntity<Users> request(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return  ResponseEntity.ok(repo.save(user));

    }

    //checks username and password correct or not validation
    public String verify(Users user) {
        Authentication authentication =
                authmanager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(), user.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {
            //this fetches all columns of that person
            Users dbUser = repo.findByUsername(user.getUsername())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found"));

            return jwtService.generateToken(dbUser);
        }

        throw new BadCredentialsException("Invalid username or password");
    }


    public List<String> alldata() {
        return repo.staffRefIds();
    }
}
