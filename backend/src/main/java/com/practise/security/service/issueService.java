package com.practise.security.service;

import com.practise.security.DTO.RaiseIssuedto;
import com.practise.security.Repo.UserRepo;
import com.practise.security.Repo.issueRepo;
import com.practise.security.model.IssueTable;
import com.practise.security.model.Users;
import com.practise.security.responcedto.staffRecordResponce;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class issueService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private issueRepo repo;

    public ResponseEntity<IssueTable> issue(RaiseIssuedto dto) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        IssueTable issuedto = new IssueTable();
        issuedto.setTitle(dto.getTitle());
        issuedto.setCategory(dto.getCategory());
        issuedto.setDescription(dto.getDescription());
        issuedto.setLocation(dto.getLocation());
        issuedto.setStatus("pending");   // ✅ default status
        issuedto.setAssigned("Unassigned");
        issuedto.setAssignedMem("");
        issuedto.setUser(user);

        IssueTable savedIssue = repo.save(issuedto);

        return ResponseEntity.ok(savedIssue);
    }

    public Map<String, Long> alldata() {
        Map<String,Long> data = new HashMap<>();
        data.put("totalIssues",repo.totalIssues());
        data.put("totalPending",repo.pendingIssues());
        data.put("totalResolved",repo.resolvedIssues());
        data.put("totalUsers",userRepo.totalUsers());

        return data;
    }

    public List<staffRecordResponce> staffrecords(Authentication authentication) {
        String username = authentication.getName();
        String refId=username.toUpperCase();
        List<IssueTable> issues = repo.Records(refId);
        return issues.stream()
                .map(i -> new staffRecordResponce(

                        i.getId(),
                        i.getUser().getRefId(),   // refId
                        i.getTitle(),
                        i.getStatus(),
                        i.getLocation()
                ))
                .toList();
    }
    @Transactional //transaction automatically save
    public void assignstaff(int issueId, String assignedMem) {

        IssueTable issue = repo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setAssignedMem(assignedMem);
        issue.setAssigned("Assigned");
    }

    public Map<String, Long> staffdetailbox(String refId) {
        String refid = refId.toUpperCase();
        Map<String,Long> data = new HashMap<>();
        data.put("totalresolved",repo.countByAssignedMemAndStatus(refid,"resolved"));
        data.put("totalpending",repo.countByAssignedMemAndStatus(refid,"pending"));
        data.put("totalissues",repo.countByAssignedMem(refid));


        return data;
    }
    @Transactional
    public void statusUpdate(int id, String status) {
        IssueTable record=repo.findById(id).orElseThrow(()-> new RuntimeException("not found user"));
        record.setStatus(status);
    }
}
