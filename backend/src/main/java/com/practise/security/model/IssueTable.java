package com.practise.security.model;

import jakarta.persistence.*;
@Entity
public class IssueTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private String status;
    private String assigned;
    private String category;
    private String location;
    private String assignedMem;

    public String getAssignedMem() {
        return assignedMem;
    }

    public void setAssignedMem(String assignedMem) {
        this.assignedMem = assignedMem;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_ref_id",           // column in IssueTable
            referencedColumnName = "refId"  // column in Users
    )
    private Users user;

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public IssueTable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
