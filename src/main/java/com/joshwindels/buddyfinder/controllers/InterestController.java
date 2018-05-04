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
        checkAuthentication();
        Optional<ExcursionDO> excursionDO = excursionRepository.getExcursionForId(excursionId);
        if (!excursionDO.isPresent()) {
            throw new RuntimeException("excursion not found");
        } else if (excursionDO.get().getOwnerId() == currentUser.getId()) {
            throw new RuntimeException("cannot express interest in own excursions");
        }
        interestRepository.expressUserInterestInExcursion(currentUser.getId(), excursionId);
        return "interest expressed";
    }

    private void checkAuthentication() {
        if (currentUser.getUsername() == null) {
            throw new RuntimeException("not authenticated");
        }
    }
}
