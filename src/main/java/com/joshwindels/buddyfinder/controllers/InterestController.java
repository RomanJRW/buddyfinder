package com.joshwindels.buddyfinder.controllers;

import java.util.List;
import java.util.Optional;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dos.InterestedUser;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
import com.joshwindels.buddyfinder.repositories.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/interest")
public class InterestController {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    ExcursionRepository excursionRepository;

    @Autowired
    InterestRepository interestRepository;

    @GetMapping("/express/{excursionId}")
    public @ResponseBody String expressInterest(@PathVariable(value = "excursionId") int excursionId) {
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

    @GetMapping("/users/{excursionId}")
    public List<InterestedUser> getInterestedUsersForExcursion(int excursionId) {
        checkAuthentication();
        Optional<ExcursionDO> excursionDO = excursionRepository.getExcursionForId(excursionId);
        if (!excursionDO.isPresent()) {
            throw new RuntimeException("excursion not found");
        } else if (excursionDO.get().getOwnerId() != currentUser.getId()) {
            throw new RuntimeException("not authorised to view interested users for excursions posted by others");
        }
        return interestRepository.getUsersInterestedInExcursion(excursionId);
    }

    private void checkAuthentication() {
        if (currentUser.getUsername() == null) {
            throw new RuntimeException("not authenticated");
        }
    }

}
