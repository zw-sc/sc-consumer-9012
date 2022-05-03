package com.sc.controller;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.hystrix.HystrixProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("consumer")
public class TestController {

    @Autowired
    private TestService testService;

    @Value("${server.port}")
    private String port;

    @RequestMapping("test/{id}")
    public Integer test(@PathVariable Long id) {
        System.out.println("访问位置：" + port);
        return testService.findDefaultResumeState(id);
    }


    @HystrixCommand(
            threadPoolKey = "test-hy",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "2"),
                    @HystrixProperty(name = "maxQueueSize", value = "20")
            },
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")
            }
//            fallbackMethod = "myFallback" // 熔断后降级方法
    )    @RequestMapping("test/hy/{id}")
    public Integer hy(@PathVariable Long id) {
        System.out.println("访问位置：" + port);
        return testService.findDefaultResumeState(id);
    }

    public Integer myFallback(@PathVariable Long id) {
        return -1;
    }
}
