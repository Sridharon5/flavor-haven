package com.flavorhaven.dto;

import java.util.List;

public class MealPlanSaveTemplatesDto {
    private Long userId;
    private List<MealPlanTemplateEntryDto> templates;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<MealPlanTemplateEntryDto> getTemplates() {
        return templates;
    }

    public void setTemplates(List<MealPlanTemplateEntryDto> templates) {
        this.templates = templates;
    }
}
