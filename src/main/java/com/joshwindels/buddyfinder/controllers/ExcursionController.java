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
        validateNewExcursion(excursionDTO);
        excursionDTO.setOwnerId(currentUser.getId());
        ExcursionDO excursionDO = excursionHelper.convertToDO(excursionDTO);
        excursionRepository.storeExcursion(excursionDO);
        return "excursion created";
    }

    @PatchMapping("/update")
    public String updateExcursion(ExcursionDTO excursionDTO) {
        validateUpdateExcursion(excursionDTO);
        ExcursionDO excursionDO = excursionHelper.convertToDO(excursionDTO);
        excursionRepository.updateExcursion(excursionDO);
        return "excursion updated";
    }

    @DeleteMapping("/delete")
    public String deleteExcursion(int excursionId) {
        checkAuthentication();
        Optional<ExcursionDO> storedExcursion = excursionRepository.getExcursionForId(excursionId);
        if (storedExcursion.isPresent()) {
            if (storedExcursion.get().getOwnerId() == currentUser.getId()) {
                excursionRepository.deleteExcursion(excursionId);
                return "excursion deleted";
            }
            throw new RuntimeException("user doesn't have permission to delete excursion");
        } else {
            throw new RuntimeException("excursion does not exist");
        }
    }

    @GetMapping("/get")
    public List<ExcursionDTO> getExcursions(ExcursionFilter filter) {
        checkAuthentication();
        validateFilter(filter);
        Map<FilterTypes, Object> filterParams = excursionHelper.extractFilterParametersFromFilter(filter);
        List<ExcursionDO> excursions = excursionRepository.getExcursionsMatchingFilterParameters(filterParams);
        return excursions.stream().map(excursionHelper::convertToDTO).collect(
                Collectors.toList());
    }

    private void validateFilter(ExcursionFilter filter) {
        if (filter.getStartDate().after(filter.getEndDate())) {
            throw new RuntimeException("end date must be after start date");
        }
    }

    private void validateNewExcursion(ExcursionDTO excursionDTO) {
        checkAuthentication();
        if (!excursionIsValid(excursionDTO)) {
            throw new RuntimeException("all fields must be provided");
        } else if (excursionDTO.getStartDate().after(excursionDTO.getEndDate())) {
            throw new RuntimeException("end date must be after start date");
        }
    }

    private void validateUpdateExcursion(ExcursionDTO excursionDTO) {
        checkAuthentication();
        validateExcursionFields(excursionDTO);
        Optional<ExcursionDO> optionalStoredExcursion = excursionRepository.getExcursionForId(
                excursionDTO.getId());
        if (!optionalStoredExcursion.isPresent()) {
            throw new RuntimeException("excursion does not exist");
        }
        ExcursionDO storedExcursion = optionalStoredExcursion.get();
        if (storedExcursion.getOwnerId() != currentUser.getId()) {
            throw new RuntimeException("user does not have permission to update this excursion");
        }
    }

    private void validateExcursionFields(ExcursionDTO excursionDTO) {
        if (!excursionIsValid(excursionDTO)) {
            throw new RuntimeException("all fields must be valid");
        } else if (excursionDTO.getStartDate().after(excursionDTO.getEndDate())) {
            throw new RuntimeException("end date must be after start date");
        }
    }

    private boolean excursionIsValid(ExcursionDTO excursionDTO) {
        if ((excursionDTO.getName() == null || excursionDTO.getName().isEmpty())
                || (excursionDTO.getStartLocation() == null || excursionDTO.getStartLocation().isEmpty())
                || (excursionDTO.getEndLocation() == null || excursionDTO.getEndLocation().isEmpty())
                || (excursionDTO.getStartDate() == null || excursionDTO.getEndDate() == null)
                || (excursionDTO.getEstimatedCost() < 0 || excursionDTO.getRequiredBuddies() < 1)
                || (excursionDTO.getDescription() == null || excursionDTO.getDescription().isEmpty())) {
            return false;
        }
        return true;
    }

    private void checkAuthentication() {
        if (currentUser.getUsername() == null) {
            throw new RuntimeException("not authenticated");
        }
    }
}
