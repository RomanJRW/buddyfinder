package com.joshwindels.buddyfinder.controllers;

import java.util.Collections;
import java.util.Map;

import com.joshwindels.buddyfinder.repositories.TestDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestDBRepository testDBRepository;

    @GetMapping("/server")
    public Map<String, String> testServerEndpoint() {
        return Collections.singletonMap("message", "Test endpoint response");
    }

    @GetMapping("/db")
    public Integer dbTestConnectionEndpoint() {
        return testDBRepository.getFirstExcursionDetails();
    }

}
