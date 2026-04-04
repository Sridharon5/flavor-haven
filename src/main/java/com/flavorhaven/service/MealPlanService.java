package com.flavorhaven.service;

import java.util.Map;

import com.flavorhaven.dto.MealPlanGetWeekDto;
import com.flavorhaven.dto.MealPlanSaveTemplatesDto;
import com.flavorhaven.dto.MealPlanSaveWeekDto;
import com.flavorhaven.dto.MealPlanUserOnlyDto;

public interface MealPlanService {

    Map<String, Object> getWeek(MealPlanGetWeekDto dto);

    Map<String, Object> saveWeek(MealPlanSaveWeekDto dto);

    Map<String, Object> listTemplates(MealPlanUserOnlyDto dto);

    Map<String, Object> saveTemplates(MealPlanSaveTemplatesDto dto);
}
