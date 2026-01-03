package com.practise.security.service;

import com.practise.security.Repo.UserRepo;
import com.practise.security.model.UserPrinciples;
import com.practise.security.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String Username){
        System.out.println("Loading user from DB: " + Username);

        Users user= repo.findByUsername(Username)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
            return new UserPrinciples(user);
    }

    public UserDetails loadUserByRefId(String refId){
        System.out.println("Loading user from DB: " + refId);

        Users user= repo.findByUsername(refId)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
        return new UserPrinciples(user);
    }
}
