package com.practise.security.service;

import com.practise.security.DTO.RaiseIssuedto;
import com.practise.security.Repo.UserRepo;
import com.practise.security.Repo.issueRepo;
import com.practise.security.model.IssueTable;
import com.practise.security.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        issuedto.setStatus("Pending");   // ✅ default status
        issuedto.setAssigned("NotAssigned");
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
}
