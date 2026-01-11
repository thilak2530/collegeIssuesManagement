package com.practise.security.Repo;

import com.practise.security.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
    public  interface UserRepo extends JpaRepository<Users,Integer> {

        @Query("SELECT COUNT(u) FROM Users u")
        long totalUsers();

        @Query("SELECT u.refId FROM Users u WHERE u.role = 'staff'")
        List<String> staffRefIds();


        Optional<Users> findByUsername(String username);
    }

