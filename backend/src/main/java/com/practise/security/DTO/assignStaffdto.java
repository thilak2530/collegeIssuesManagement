package com.practise.security.DTO;

public class assignStaffdto {
    private String assignedMem;
    private int issueId;

    public String getAssignedMem() {
        return assignedMem;
    }

    public int getIssueId() {
        return issueId;
    }

    public assignStaffdto( int id ,String assignedMem) {
        this.assignedMem = assignedMem;
        this.issueId = id;
    }
}
