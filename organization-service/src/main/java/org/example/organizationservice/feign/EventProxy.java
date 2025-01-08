package org.example.organizationservice.feign;

import org.example.organizationservice.dto.CommentsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "event-service", url = "localhost:8083/api/event/")
public interface EventProxy {

    @GetMapping("/com-org/{organizationId}")
    public List<CommentsDto> getCommentsByOrganizationId(@PathVariable Integer organizationId);

}
