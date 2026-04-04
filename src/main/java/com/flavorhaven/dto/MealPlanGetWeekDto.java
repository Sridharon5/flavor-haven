package com.flavorhaven.dto;

public class MealPlanGetWeekDto {
    private Long userId;
    private String weekId;

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
}
