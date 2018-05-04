package com.joshwindels.buddyfinder.controllers;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.repositories.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InterestController {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    InterestRepository interestRepository;

    public String expressInterest(int excursionId) {
        interestRepository.expressUserInterestInExcursion(currentUser.getId(), excursionId);
        return "interest expressed";
    }
}
