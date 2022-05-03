package com.sc.service.impl;

import com.sc.service.TestService;
import org.springframework.stereotype.Component;

@Component
public class TestServiceImpl implements TestService {
    @Override
    public Integer findDefaultResumeState(Long id) {
        return 0;
    }
}
