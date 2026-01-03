package com.practise.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MainTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String roles;
    private String issue_update;
    private String staff_assigned;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getIssue_update() {
        return issue_update;
    }

    public void setIssue_update(String issue_update) {
        this.issue_update = issue_update;
    }

    public String getStaff_assigned() {
        return staff_assigned;
    }

    public void setStaff_assigned(String staff_assigned) {
        this.staff_assigned = staff_assigned;
    }
}
