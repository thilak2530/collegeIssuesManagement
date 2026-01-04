package com.practise.security.Repo;

import com.practise.security.model.IssueTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface issueRepo extends JpaRepository<IssueTable,Integer> {
    @Query("SELECT COUNT(i) FROM IssueTable i")
    long totalIssues();

    @Query("SELECT COUNT(i) FROM IssueTable i WHERE i.status = 'pending'")
    long pendingIssues();

    @Query("SELECT COUNT(i) FROM IssueTable i WHERE i.status = 'resolved'")
    long resolvedIssues();

    @Query("SELECT i FROM IssueTable i WHERE i.assignedMem = :refId")
    List<IssueTable> Records(@Param("refId") String refId);


}
