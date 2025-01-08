package org.example.eventservice.dto;

import org.example.eventservice.model.Event;

import java.time.Instant;

public class CommentDto {

    private Integer id;
//    private Event event;
    private String content;
    private Byte rating;
    private Instant createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Event getEvent() {
//        return event;
//    }
//
//    public void setEvent(Event event) {
//        this.event = event;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
