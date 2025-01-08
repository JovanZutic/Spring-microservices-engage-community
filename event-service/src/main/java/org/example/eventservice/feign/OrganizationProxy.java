package org.example.eventservice.feign;

import org.example.eventservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "organization-service", url = "localhost:8082/api/org/")
public interface OrganizationProxy {

//    @GetMapping("/{id}")
//    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable Integer id);

}
