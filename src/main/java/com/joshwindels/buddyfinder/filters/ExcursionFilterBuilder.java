package com.joshwindels.buddyfinder.filters;

import java.util.Date;

public class ExcursionFilterBuilder {
    private ExcursionFilter excursionFilter = new ExcursionFilter();

    public ExcursionFilterBuilder nameContains(String name) {
        excursionFilter.setNameContains(name);
        return this;
    }

    public ExcursionFilterBuilder startLocationContains(String startLocation) {
        excursionFilter.setStartLocationContains(startLocation);
        return this;
    }

    public ExcursionFilterBuilder endLocationContains(String endLocation) {
        excursionFilter.setEndLocationContains(endLocation);
        return this;
    }

    public ExcursionFilterBuilder startDate(Date startDate) {
        excursionFilter.setStartDate(startDate);
        return this;
    }

    public ExcursionFilterBuilder endDate(Date endDate){
        excursionFilter.setEndDate(endDate);
        return this;
    }

    public ExcursionFilterBuilder minEstimatedCost(double estimatedCost) {
        excursionFilter.setMinEstimatedCost(estimatedCost);
        return this;
    }

    public ExcursionFilterBuilder maxEstimatedCost(double estimatedCost) {
        excursionFilter.setMaxEstimatedCost(estimatedCost);
        return this;
    }

    public ExcursionFilterBuilder minRequiredBuddies(int requiredBuddies) {
        excursionFilter.setMinRequiredBuddies(requiredBuddies);
        return this;
    }

    public ExcursionFilterBuilder maxRequiredBuddies(int requiredBuddies) {
        excursionFilter.setMaxRequiredBuddies(requiredBuddies);
        return this;
    }

    public ExcursionFilterBuilder descriptonContains(String description) {
        excursionFilter.setDescriptionContains(description);
        return this;
    }

    public ExcursionFilterBuilder postedBy(String username) {
        excursionFilter.setPostedBy(username);
        return this;
    }

    public ExcursionFilter build() {
        return excursionFilter;
    }

}
