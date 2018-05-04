package com.joshwindels.buddyfinder.filters;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ExcursionFilter {

    private String nameContains;
    private String startLocationContains;
    private String endLocationContains;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;
    private Double minEstimatedCost;
    private Double maxEstimatedCost;
    private Integer minRequiredBuddies;
    private Integer maxRequiredBuddies;
    private String descriptionContains;
    private Integer postedBy;

    public String getNameContains() {
        return nameContains;
    }

    public void setNameContains(String nameContains) {
        this.nameContains = nameContains;
    }

    public String getStartLocationContains() {
        return startLocationContains;
    }

    public void setStartLocationContains(String startLocationContains) {
        this.startLocationContains = startLocationContains;
    }

    public String getEndLocationContains() {
        return endLocationContains;
    }

    public void setEndLocationContains(String endLocationContains) {
        this.endLocationContains = endLocationContains;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getMinEstimatedCost() {
        return minEstimatedCost;
    }

    public void setMinEstimatedCost(Double minEstimatedCost) {
        this.minEstimatedCost = minEstimatedCost;
    }

    public Double getMaxEstimatedCost() {
        return maxEstimatedCost;
    }

    public void setMaxEstimatedCost(Double maxEstimatedCost) {
        this.maxEstimatedCost = maxEstimatedCost;
    }

    public Integer getMinRequiredBuddies() {
        return minRequiredBuddies;
    }

    public void setMinRequiredBuddies(Integer minRequiredBuddies) {
        this.minRequiredBuddies = minRequiredBuddies;
    }

    public Integer getMaxRequiredBuddies() {
        return maxRequiredBuddies;
    }

    public void setMaxRequiredBuddies(Integer maxRequiredBuddies) {
        this.maxRequiredBuddies = maxRequiredBuddies;
    }

    public String getDescriptionContains() {
        return descriptionContains;
    }

    public void setDescriptionContains(String descriptionContains) {
        this.descriptionContains = descriptionContains;
    }

    public Integer getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(int postedBy) {
        this.postedBy = postedBy;
    }
}
