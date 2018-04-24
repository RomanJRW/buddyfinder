package com.joshwindels.buddyfinder.dos;

import java.util.Date;

public class ExcursionDOBuilder {

    private ExcursionDO excursionDTO = new ExcursionDO();

    public ExcursionDOBuilder id(int id) {
        excursionDTO.setId(id);
        return this;
    }

    public ExcursionDOBuilder ownerId(int ownerId) {
        excursionDTO.setOwnerId(ownerId);
        return this;
    }

    public ExcursionDOBuilder name(String name) {
        excursionDTO.setName(name);
        return this;
    }

    public ExcursionDOBuilder startLocation(String startLocation) {
        excursionDTO.setStartLocation(startLocation);
        return this;
    }

    public ExcursionDOBuilder endLocation(String endLocation) {
        excursionDTO.setEndLocation(endLocation);
        return this;
    }

    public ExcursionDOBuilder startDate(Date startDate) {
        excursionDTO.setStartDate(startDate);
        return this;
    }

    public ExcursionDOBuilder endDate(Date endDate) {
        excursionDTO.setEndDate(endDate);
        return this;
    }

    public ExcursionDOBuilder estimatedCost(double estimatedCost) {
        excursionDTO.setEstimatedCost(estimatedCost);
        return this;
    }

    public ExcursionDOBuilder requiredBuddies(int requiredBuddies) {
        excursionDTO.setRequiredBuddies(requiredBuddies);
        return this;
    }

    public ExcursionDOBuilder description(String description) {
        excursionDTO.setDescription(description);
        return this;
    }

    public ExcursionDO build() {
        return excursionDTO;
    }
}
