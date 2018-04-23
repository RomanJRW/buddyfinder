package com.joshwindels.buddyfinder.dtos;

import java.util.Date;

public class ExcursionDTOBuilder {
    private ExcursionDTO excursionDTO = new ExcursionDTO();

    public ExcursionDTOBuilder id(int id) {
        excursionDTO.setId(id);
        return this;
    }

    public ExcursionDTOBuilder ownerId(int ownerId) {
        excursionDTO.setOwnerId(ownerId);
        return this;
    }

    public ExcursionDTOBuilder name(String name) {
        excursionDTO.setName(name);
        return this;
    }

    public ExcursionDTOBuilder startLocation(String startLocation) {
        excursionDTO.setStartLocation(startLocation);
        return this;
    }

    public ExcursionDTOBuilder endLocation(String endLocation) {
        excursionDTO.setEndLocation(endLocation);
        return this;
    }

    public ExcursionDTOBuilder startDate(Date startDate) {
        excursionDTO.setStartDate(startDate);
        return this;
    }

    public ExcursionDTOBuilder endDate(Date endDate) {
        excursionDTO.setEndDate(endDate);
        return this;
    }

    public ExcursionDTOBuilder estimatedCost(double estimatedCost) {
        excursionDTO.setEstimatedCost(estimatedCost);
        return this;
    }

    public ExcursionDTOBuilder requiredBuddies(int requiredBuddies) {
        excursionDTO.setRequiredBuddies(requiredBuddies);
        return this;
    }

    public ExcursionDTOBuilder description(String description) {
        excursionDTO.setDescription(description);
        return this;
    }

    public ExcursionDTO build() {
        return excursionDTO;
    }
}

