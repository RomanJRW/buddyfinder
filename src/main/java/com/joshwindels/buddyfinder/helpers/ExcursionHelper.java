package com.joshwindels.buddyfinder.helpers;

import java.util.HashMap;
import java.util.Map;

import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import com.joshwindels.buddyfinder.filters.ExcursionFilter;
import org.springframework.stereotype.Component;

@Component
public class ExcursionHelper {

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

    public Map<FilterTypes, Object> extractFilterParametersFromFilter(ExcursionFilter filter) {
        Map<FilterTypes, Object> filterparams = new HashMap<>();
        if (filter.getNameContains() != null) {
            filterparams.put(FilterTypes.NAME_CONTAINS, filter.getNameContains());
        }
        if (filter.getStartLocationContains() != null) {
            filterparams.put(FilterTypes.START_LOCATION_CONTAINS, filter.getStartLocationContains());
        }
        if (filter.getEndLocationContains() != null) {
            filterparams.put(FilterTypes.END_LOCATION_CONTAINS, filter.getEndLocationContains());
        }
        if (filter.getStartDate() != null) {
            filterparams.put(FilterTypes.START_DATE, filter.getStartDate());
        }
        if (filter.getEndDate() != null) {
            filterparams.put(FilterTypes.END_DATE, filter.getEndDate());
        }
        if (filter.getMinEstimatedCost() != null) {
            filterparams.put(FilterTypes.MIN_ESTIMATED_COST, filter.getMinEstimatedCost());
        }
        if (filter.getMaxEstimatedCost() != null) {
            filterparams.put(FilterTypes.MAX_ESTIMATED_COST, filter.getMaxEstimatedCost());
        }
        if (filter.getMinRequiredBuddies() != null) {
            filterparams.put(FilterTypes.MIN_REQUIRED_BUDDIES, filter.getMinRequiredBuddies());
        }
        if (filter.getMaxRequiredBuddies() != null) {
            filterparams.put(FilterTypes.MAX_REQUIRED_BUDDIES, filter.getMaxRequiredBuddies());
        }
        if (filter.getDescriptionContains() != null) {
            filterparams.put(FilterTypes.DESCRIPTION_CONTAINS, filter.getDescriptionContains());
        }
        if (filter.getDescriptionContains() != null) {
            filterparams.put(FilterTypes.POSTED_BY, filter.getPostedBy());
        }
        return filterparams;
    }

}
