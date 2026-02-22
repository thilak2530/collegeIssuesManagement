package com.practise.security.service;

import com.practise.security.DTO.RaiseIssuedto;
import com.practise.security.DTO.totalIssuesdto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class issueService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private issueRepo repo;

    public  IssueTable issue(RaiseIssuedto dto, MultipartFile img) throws IOException {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        IssueTable issue = new IssueTable();
        issue.setTitle(dto.getTitle());
        issue.setCategory(dto.getCategory());
        issue.setDescription(dto.getDescription());
        issue.setLocation(dto.getLocation());
        issue.setStatus("pending");   // ✅ default status
        issue.setAssigned("Unassigned");
        issue.setAssignedMem("");
        issue.setUser(user);
        if (img != null && !img.isEmpty()) {
            issue.setImageData(img.getBytes()); // ✅ image stored as BYTEA
        }

        return repo.save(issue);
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
                        i.getLocation(),
                        i.getImageData()!=null ? Base64.getEncoder().encodeToString(i.getImageData()) : null
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



    public List<totalIssuesdto> getAllIssues() {
        List<IssueTable> issuess=repo.findAll();
        List<totalIssuesdto> dtoList= issuess.stream().map(issue ->{
            totalIssuesdto dto=new totalIssuesdto();
            dto.setId(issue.getId());
            dto.setTitle(issue.getTitle());
            dto.setStatus(issue.getStatus());
            dto.setLocation(issue.getLocation());
            dto.setAssigned(issue.getAssigned());

            if(issue.getUser()!=null){
                dto.setRefId(issue.getUser().getRefId());
            }
            if(issue.getImageData()!=null){
                String base64= Base64.getEncoder().encodeToString(issue.getImageData());
                dto.setImageData(base64);
            }
            return dto;
        }).toList();
        return dtoList;
    }
}
