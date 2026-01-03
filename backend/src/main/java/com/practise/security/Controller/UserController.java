package com.practise.security.Controller;


import com.practise.security.DTO.RaiseIssuedto;
import com.practise.security.Repo.issueRepo;
import com.practise.security.model.IssueTable;
import com.practise.security.model.Users;
import com.practise.security.service.UserService;
import com.practise.security.service.issueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private issueService issueservice;

    @Autowired
    private issueRepo issuerepo;

    @PostMapping("/userpass")
    public ResponseEntity<Users> Request(@RequestBody Users user){
        return userService.request(user);
    }

    @PostMapping("/login")

    public String login(@RequestBody Users user){
        System.out.println("hello"+user.getUsername()+user.getPassword());

        return userService.verify(user);
    }
    @PostMapping("/raiseIssue")
    public ResponseEntity<IssueTable> addingIssue(@RequestBody RaiseIssuedto issue){
        return issueservice.issue(issue);
    }
    @GetMapping("/totalIssues")
    public List<IssueTable> totalIssues(){
        return issuerepo.findAll();
    }

    @GetMapping("/data")
    public ResponseEntity<Map<String , Long>> data(){
        Map<String,Long> data=issueservice.alldata();
        return ResponseEntity.ok(data);
    }

}
