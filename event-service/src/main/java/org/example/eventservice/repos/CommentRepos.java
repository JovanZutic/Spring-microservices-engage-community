package org.example.eventservice.repos;

import org.example.eventservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepos extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c JOIN Event e ON c.event.id = e.id WHERE e.organizationId = :orgId")
    List<Comment> findCommentsByOrganizationId(@Param("orgId") Integer orgId);
}
