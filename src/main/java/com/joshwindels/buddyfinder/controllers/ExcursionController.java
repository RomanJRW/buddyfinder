package com.joshwindels.buddyfinder.controllers;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/excursions")
public class ExcursionController {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExcursionRepository excursionRepository;

    @PostMapping("/create")
    public @ResponseBody String createExcursion(ExcursionDTO excursionDTO) {
        if (currentUser.getUsername() == null) {
            return "not authenticated";
        } else if (!excursionIsValid(excursionDTO)) {
            return "all fields must be provided";
        } else {
            excursionDTO.setOwnerId(currentUser.getId());
            ExcursionDO excursionDO = convertToDo(excursionDTO);
            excursionRepository.storeExcursion(excursionDO);
            return "excursion created";
        }
    }

    private boolean excursionIsValid(ExcursionDTO excursionDTO) {
        if (excursionDTO.getName() == null || excursionDTO.getName().isEmpty()) {
            return false;
        } else if (excursionDTO.getStartLocation() == null || excursionDTO.getStartLocation().isEmpty()) {
            return false;
        } else if (excursionDTO.getEndLocation() == null || excursionDTO.getEndLocation().isEmpty()) {
            return false;
        }
        return true;
    }

    private ExcursionDO convertToDo(ExcursionDTO excursionDTO) {
        ExcursionDO excursionDO = new ExcursionDO();
        excursionDO.setId(excursionDTO.getId());
        excursionDO.setOwnerId(excursionDTO.getOwnerId());
        excursionDO.setName(excursionDTO.getName());
        excursionDO.setStartLocation(excursionDTO.getStartLocation());
        excursionDO.setEndLocation(excursionDTO.getEndLocation());
        excursionDO.setStartDate(excursionDTO.getStartDate());
        excursionDO.setEndDate(excursionDTO.getEndDate());
        excursionDO.setEstimatedCost(excursionDTO.getEstimatedCost());
        excursionDO.setRequiredBuddies(excursionDTO.getRequiredBuddies());
        excursionDO.setDescription(excursionDTO.getDescription());
        return excursionDO;
    }

}
