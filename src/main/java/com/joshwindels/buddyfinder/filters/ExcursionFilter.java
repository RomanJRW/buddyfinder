package com.joshwindels.buddyfinder.filters;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ExcursionFilter {

    private String nameContains;
    private String startLocationContains;
    private String endLocationContains;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;
    private double minEstimatedCost;
    private double maxEstimatedCost;
    private int minRequiredBuddies;
    private int maxRequiredBuddies;
    private String descriptionContains;

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

    public void setEndDate(Date startDate) {
        this.endDate = endDate;
    }

    public double getMinEstimatedCost() {
        return minEstimatedCost;
    }

    public void setMinEstimatedCost(double minEstimatedCost) {
        this.minEstimatedCost = minEstimatedCost;
    }

    public double getMaxEstimatedCost() {
        return maxEstimatedCost;
    }

    public void setMaxEstimatedCost(double maxEstimatedCost) {
        this.maxEstimatedCost = maxEstimatedCost;
    }

    public int getMinRequiredBuddies() {
        return minRequiredBuddies;
    }

    public void setMinRequiredBuddies(int minRequiredBuddies) {
        this.minRequiredBuddies = minRequiredBuddies;
    }

    public int getMaxRequiredBuddies() {
        return maxRequiredBuddies;
    }

    public void setMaxRequiredBuddies(int maxRequiredBuddies) {
        this.maxRequiredBuddies = maxRequiredBuddies;
    }

    public String getDescriptionContains() {
        return descriptionContains;
    }

    public void setDescriptionContains(String descriptionContains) {
        this.descriptionContains = descriptionContains;
    }
}
