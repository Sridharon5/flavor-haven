package com.flavorhaven.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class MealPlanSaveWeekDto {
    private Long userId;
    private String weekId;
    private JsonNode days;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWeekId() {
        return weekId;
    }

    public void setWeekId(String weekId) {
        this.weekId = weekId;
    }

    public JsonNode getDays() {
        return days;
    }

    public void setDays(JsonNode days) {
        this.days = days;
    }
}
