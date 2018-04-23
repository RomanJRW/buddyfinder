package com.joshwindels.buddyfinder.controllers;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
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
    ExcursionRepository excursionRepository;

    @PostMapping("/create")
    public @ResponseBody String createExcursion(ExcursionDTO excursionDTO) {
        ExcursionDO excursionDO = convertToDo(excursionDTO);
        excursionRepository.storeExcursion(excursionDO);

        return "excursion created";
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
