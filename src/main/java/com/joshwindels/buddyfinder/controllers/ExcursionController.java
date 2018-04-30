package com.joshwindels.buddyfinder.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import com.joshwindels.buddyfinder.filters.ExcursionFilter;
import com.joshwindels.buddyfinder.helpers.ExcursionHelper;
import com.joshwindels.buddyfinder.helpers.FilterTypes;
import com.joshwindels.buddyfinder.repositories.ExcursionRepository;
import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired ExcursionHelper excursionHelper;


    @PostMapping("/create")
    public @ResponseBody String createExcursion(ExcursionDTO excursionDTO) {
        Optional<String> validationErrorMessage = getCreateExcursionErrorMessage(excursionDTO);
        if (validationErrorMessage.isPresent()) {
            return validationErrorMessage.get();
        }
        excursionDTO.setOwnerId(currentUser.getId());
        ExcursionDO excursionDO = excursionHelper.convertToDO(excursionDTO);
        excursionRepository.storeExcursion(excursionDO);
        return "excursion created";
    }

    @PatchMapping("/update")
    public String updateExcursion(ExcursionDTO excursionDTO) {
        Optional<String> validationErrorMessage = getUpdateExcursionErrorMessage(excursionDTO);
        if (validationErrorMessage.isPresent()) {
            return validationErrorMessage.get();
        }
        ExcursionDO excursionDO = excursionHelper.convertToDO(excursionDTO);
        excursionRepository.updateExcursion(excursionDO);
        return "excursion updated";
    }

    @DeleteMapping("/delete")
    public String deleteExcursion(int excursionId) {
        if (currentUser.getUsername() == null) {
            return "not authenticated";
        }
        Optional<ExcursionDO> storedExcursion = excursionRepository.getExcursionForId(excursionId);
        if (storedExcursion.isPresent()) {
            if (storedExcursion.get().getOwnerId() == currentUser.getId()) {
                excursionRepository.deleteExcursion(excursionId);
                return "excursion deleted";
            }
            return "user doesn't have permission to delete excursion";
        } else {
            return "excursion does not exist";
        }
    }

    @GetMapping("/get")
    public List<ExcursionDTO> getExcursions(ExcursionFilter filter) {
        if (currentUser.getUsername() == null) {
            //return "not authenticated";
        }
        Map<FilterTypes, Object> filterParams = excursionHelper.extractFilterParametersFromFilter(filter);
        List<ExcursionDO> excursions = excursionRepository.getExcursionsMatchingFilterParameters(filterParams);
        return excursions.stream().map(excursionHelper::convertToDTO).collect(
                Collectors.toList());
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
}
