package com.joshwindels.buddyfinder.helpers;

import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import org.springframework.stereotype.Component;

@Component
public class ExcursionConverter {

    public ExcursionDO convertToDO(ExcursionDTO excursionDTO) {
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

    public ExcursionDTO convertToDTO(ExcursionDO excursionDO) {
        ExcursionDTO excursionDTO = new ExcursionDTO();
        excursionDTO.setId(excursionDO.getId());
        excursionDTO.setOwnerId(excursionDO.getOwnerId());
        excursionDTO.setName(excursionDO.getName());
        excursionDTO.setStartLocation(excursionDO.getStartLocation());
        excursionDTO.setEndLocation(excursionDO.getEndLocation());
        excursionDTO.setStartDate(excursionDO.getStartDate());
        excursionDTO.setEndDate(excursionDO.getEndDate());
        excursionDTO.setEstimatedCost(excursionDO.getEstimatedCost());
        excursionDTO.setRequiredBuddies(excursionDO.getRequiredBuddies());
        excursionDTO.setDescription(excursionDO.getDescription());
        return excursionDTO;
    }

}
