package com.practise.security.Controller;


import com.practise.security.DTO.RaiseIssuedto;
import com.practise.security.DTO.assignStaffdto;
import com.practise.security.DTO.totalIssuesdto;
import com.practise.security.DTO.updateStatusdto;
import com.practise.security.Repo.issueRepo;
import com.practise.security.model.IssueTable;
import com.practise.security.model.Users;
import com.practise.security.responcedto.staffRecordResponce;
import com.practise.security.service.UserService;
import com.practise.security.service.issueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(
            value = "/raiseIssue",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<IssueTable> addingIssue(@RequestPart("issue") RaiseIssuedto dto, @RequestPart(value = "img", required = false) MultipartFile img){

        try {
            IssueTable issue = issueservice.issue(dto, img);
            return ResponseEntity.ok(issue);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/totalIssues")
    public List<totalIssuesdto> totalIssues(){
        return issueservice.getAllIssues();
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
        issueservice.assignstaff(body.getIssueId(), body.getAssignedMem());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/staffdetailboxs")
    public ResponseEntity<Map<String , Long>> staffdetailbox(Authentication authentication){
        String refId=authentication.getName();
        System.out.println("ref id isssssss"+authentication.getName());
        Map<String,Long> data=issueservice.staffdetailbox(refId);

        return ResponseEntity.ok(data);
    }

    @PatchMapping("/status/update")
    public ResponseEntity<Void> updateStatus(@RequestBody updateStatusdto body){
        System.out.println(body.getId());
        issueservice.statusUpdate(body.getId(),body.getStatus());
        return ResponseEntity.ok().build();

    }

}
