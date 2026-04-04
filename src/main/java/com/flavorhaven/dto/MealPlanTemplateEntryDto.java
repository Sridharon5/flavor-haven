package com.flavorhaven.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class MealPlanTemplateEntryDto {
    private String name;
    private JsonNode days;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonNode getDays() {
        return days;
    }

    public void setDays(JsonNode days) {
        this.days = days;
    }
}
