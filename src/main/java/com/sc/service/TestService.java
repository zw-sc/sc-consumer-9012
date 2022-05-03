package com.sc.service;


import com.sc.service.impl.TestServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "sc-producer", fallback = TestServiceImpl.class, path = "/resume")
public interface TestService {

    @GetMapping("/openstate/{id}")
    public Integer findDefaultResumeState(@PathVariable Long id) ;
}
