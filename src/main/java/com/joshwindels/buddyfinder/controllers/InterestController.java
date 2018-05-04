package com.joshwindels.buddyfinder.controllers;

import java.util.Optional;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
import com.joshwindels.buddyfinder.repositories.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InterestController {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    ExcursionRepository excursionRepository;

    @Autowired
    InterestRepository interestRepository;

    public String expressInterest(int excursionId) {
        Optional<ExcursionDO> excursionDO = excursionRepository.getExcursionForId(excursionId);
        if (!excursionDO.isPresent()) {
            throw new RuntimeException("excursion not found");
        }
        interestRepository.expressUserInterestInExcursion(currentUser.getId(), excursionId);
        return "interest expressed";
    }
}
