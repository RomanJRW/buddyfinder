package com.joshwindels.buddyfinder.controllers;

import java.util.Optional;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
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
        Optional<String> validationErrorMessage = getCreateExcursionErrorMessage(excursionDTO);
        if (validationErrorMessage.isPresent()) {
            return validationErrorMessage.get();
        }
        excursionDTO.setOwnerId(currentUser.getId());
        ExcursionDO excursionDO = convertToDo(excursionDTO);
        excursionRepository.storeExcursion(excursionDO);
        return "excursion created";
    }

    @PatchMapping("/update")
    public String updateExcursion(ExcursionDTO excursionDTO) {
        Optional<String> validationErrorMessage = getUpdateExcursionErrorMessage(excursionDTO);
        if (validationErrorMessage.isPresent()) {
            return validationErrorMessage.get();
        }
        ExcursionDO excursionDO = convertToDo(excursionDTO);
        excursionRepository.updateExcursion(excursionDO);
        return "excursion updated";
    }

    private Optional<String> getCreateExcursionErrorMessage(ExcursionDTO excursionDTO) {
        if (currentUser.getUsername() == null) {
            return Optional.of("not authenticated");
        } else if (!excursionIsValid(excursionDTO)) {
            return Optional.of("all fields must be provided");
        } else if (excursionDTO.getStartDate().after(excursionDTO.getEndDate())) {
            return Optional.of("end date must be after start date");
        }
        return Optional.empty();
    }

    private Optional<String> getUpdateExcursionErrorMessage(ExcursionDTO excursionDTO) {
        if (currentUser.getUsername() == null) {
            return Optional.of("not authenticated");
        }
        Optional<String> validationErrorMessage = getFieldValidationErrorMessage(excursionDTO);
        if (validationErrorMessage.isPresent()) {
            return validationErrorMessage;
        }
        Optional<ExcursionDO> optionalStoredExcursion = excursionRepository.getExcursionForId(
                excursionDTO.getId());
        if (!optionalStoredExcursion.isPresent()) {
            return Optional.of("excursion does not exist");
        }
        ExcursionDO storedExcursion = optionalStoredExcursion.get();
        if (storedExcursion.getOwnerId() != currentUser.getId()) {
            return Optional.of("user does not have permission to update this excursion");
        }
        return Optional.empty();
    }

    private Optional<String> getFieldValidationErrorMessage(ExcursionDTO excursionDTO) {
        if (!excursionIsValid(excursionDTO)) {
            return Optional.of("all fields must be valid");
        } else if (excursionDTO.getStartDate().after(excursionDTO.getEndDate())) {
            return Optional.of("end date must be after start date");
        }
        return Optional.empty();
    }

    private boolean excursionIsValid(ExcursionDTO excursionDTO) {
        if (excursionDTO.getName() == null || excursionDTO.getName().isEmpty()) {
            return false;
        } else if (excursionDTO.getStartLocation() == null || excursionDTO.getStartLocation().isEmpty()) {
            return false;
        } else if (excursionDTO.getEndLocation() == null || excursionDTO.getEndLocation().isEmpty()) {
            return false;
        } else if (excursionDTO.getStartDate() == null || excursionDTO.getEndDate() == null) {
            return false;
        } else if (excursionDTO.getEstimatedCost() < 0 || excursionDTO.getRequiredBuddies() < 1) {
            return false;
        } else if (excursionDTO.getDescription() == null || excursionDTO.getDescription().isEmpty()) {
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
