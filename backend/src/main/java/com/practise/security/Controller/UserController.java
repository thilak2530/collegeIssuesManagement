package com.practise.security.Controller;


import com.practise.security.DTO.RaiseIssuedto;
import com.practise.security.DTO.assignStaffdto;
import com.practise.security.Repo.issueRepo;
import com.practise.security.model.IssueTable;
import com.practise.security.model.Users;
import com.practise.security.responcedto.staffRecordResponce;
import com.practise.security.service.UserService;
import com.practise.security.service.issueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/staffRefIds")
    public ResponseEntity<List<String>> staffRefIds(){
        List<String> data=userService.alldata();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/staffrecords")
    public ResponseEntity<List<staffRecordResponce>> staffRecords(Authentication authentication) {
        List<staffRecordResponce> data=issueservice.staffrecords(authentication);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/issues/assign")
    public ResponseEntity<Void> assignstaff(@RequestBody assignStaffdto body){
        System.out.println("id id:"+body.getIssueId());
        issueservice.assignstaff(body.getIssueId(), body.getAssignedMem());
        return ResponseEntity.ok().build();
    }

}
