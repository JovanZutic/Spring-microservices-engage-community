package org.example.eventservice.controller;

import org.example.eventservice.dto.CommentDto;
import org.example.eventservice.exceptions.CommentsNotFoundException;
import org.example.eventservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/com")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        try {
            List<CommentDto> comments = eventService.getAllComments();
            if (comments.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(comments);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/com-org/{organizationId}")
    public ResponseEntity<List<CommentDto>> getCommentsByOrganizationId(
            @PathVariable Integer organizationId) {
        try {
            List<CommentDto> comments = eventService.getCommentsByOrganizationId(organizationId);
            return ResponseEntity.ok(comments);
        } catch (CommentsNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{organizationId}/sentiment")
    public ResponseEntity<List<CommentDto>> getCommentsBySentiment(@PathVariable Integer organizationId, @RequestParam String sentiment) {
        try {
            // Validacija parametra sentiment
            if (!sentiment.equalsIgnoreCase("positive") && !sentiment.equalsIgnoreCase("negative")) {
                return ResponseEntity.badRequest()
                        .body(Collections.emptyList());
            }

            List<CommentDto> comments = eventService.getCommentsBySentiment(organizationId, sentiment.toLowerCase());

            if (comments.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(comments);
        } catch (CommentsNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
