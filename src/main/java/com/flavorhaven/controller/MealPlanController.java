package com.flavorhaven.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flavorhaven.dto.MealPlanGetWeekDto;
import com.flavorhaven.dto.MealPlanSaveTemplatesDto;
import com.flavorhaven.dto.MealPlanSaveWeekDto;
import com.flavorhaven.dto.MealPlanUserOnlyDto;
import com.flavorhaven.service.MealPlanService;

@RestController
@RequestMapping("/api/meal-plan")
@CrossOrigin(origins = "*")
public class MealPlanController {

    private final MealPlanService mealPlanService;

    public MealPlanController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
    }

    @PostMapping("/getWeek")
    public ResponseEntity<Map<String, Object>> getWeek(@RequestBody MealPlanGetWeekDto body) {
        return ResponseEntity.ok(mealPlanService.getWeek(body));
    }

    @PostMapping("/saveWeek")
    public ResponseEntity<Map<String, Object>> saveWeek(@RequestBody MealPlanSaveWeekDto body) {
        return ResponseEntity.ok(mealPlanService.saveWeek(body));
    }

    @PostMapping("/listTemplates")
    public ResponseEntity<Map<String, Object>> listTemplates(@RequestBody MealPlanUserOnlyDto body) {
        return ResponseEntity.ok(mealPlanService.listTemplates(body));
    }

    @PostMapping("/saveTemplates")
    public ResponseEntity<Map<String, Object>> saveTemplates(@RequestBody MealPlanSaveTemplatesDto body) {
        return ResponseEntity.ok(mealPlanService.saveTemplates(body));
    }
}
