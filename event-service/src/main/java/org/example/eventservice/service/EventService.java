package org.example.eventservice.service;

import org.example.eventservice.dto.CommentDto;
import org.example.eventservice.dto.OrganizationDto;
import org.example.eventservice.exceptions.CommentsNotFoundException;
import org.example.eventservice.feign.OrganizationProxy;
import org.example.eventservice.model.Comment;
import org.example.eventservice.repos.CommentRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private CommentRepos commentRepos;

    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepos.findAll();
        return comments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByOrganizationId(Integer organizationId) {
        List<Comment> comments = commentRepos.findCommentsByOrganizationId(organizationId);

        if (comments == null || comments.isEmpty()) {
            throw new CommentsNotFoundException("No comments found for organization with ID " + organizationId);
        }

        return comments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsBySentiment(Integer organizationId, String sentiment) {
        List<Comment> allComments = commentRepos.findCommentsByOrganizationId(organizationId);
        List<CommentDto> retComments = new ArrayList<>();
        if(sentiment.equals("positive")){
            for (Comment c : allComments) {
                if(c.getRating() > 3){
                    retComments.add(mapToDto(c));
                }
            }
            return retComments;
        }else{
            for (Comment c : allComments) {
                if(c.getRating() <= 3){
                    retComments.add(mapToDto(c));
                }
            }
            return retComments;
        }
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
//        dto.setEvent(comment.getEvent());
        dto.setContent(comment.getContent());
        dto.setRating(comment.getRating());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

}
