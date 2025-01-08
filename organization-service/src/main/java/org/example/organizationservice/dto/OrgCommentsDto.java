package org.example.organizationservice.dto;

import java.util.List;

public class OrgCommentsDto {

    private List<CommentsDto> comment;
    private OrganizationDto organization;

    public List<CommentsDto> getComment() {
        return comment;
    }

    public void setComment(List<CommentsDto> comment) {
        this.comment = comment;
    }

    public OrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDto organization) {
        this.organization = organization;
    }
}
