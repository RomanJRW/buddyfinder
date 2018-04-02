package com.joshwindels.buddyfinder.controllers;

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
    public String testServerEndpoint() {
        return "buddyfinder test endpoint";
    }

    @GetMapping("/db")
    public Integer dbTestConnectionEndpoint() {
        return testDBRepository.getFirstExcursionDetails();
    }

}
