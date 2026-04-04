package com.flavorhaven.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flavorhaven.dto.MealPlanGetWeekDto;
import com.flavorhaven.dto.MealPlanSaveTemplatesDto;
import com.flavorhaven.dto.MealPlanSaveWeekDto;
import com.flavorhaven.dto.MealPlanTemplateEntryDto;
import com.flavorhaven.dto.MealPlanUserOnlyDto;
import com.flavorhaven.entity.UserEntity;
import com.flavorhaven.entity.UserMealTemplateEntity;
import com.flavorhaven.entity.UserMealWeekEntity;
import com.flavorhaven.repository.UserMealTemplateRepository;
import com.flavorhaven.repository.UserMealWeekRepository;
import com.flavorhaven.repository.UserRepository;
import com.flavorhaven.service.MealPlanService;

@Service
public class MealPlanServiceImpl implements MealPlanService {

    private final UserRepository userRepository;
    private final UserMealWeekRepository mealWeekRepository;
    private final UserMealTemplateRepository mealTemplateRepository;
    private final ObjectMapper objectMapper;

    public MealPlanServiceImpl(
            UserRepository userRepository,
            UserMealWeekRepository mealWeekRepository,
            UserMealTemplateRepository mealTemplateRepository,
            ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.mealWeekRepository = mealWeekRepository;
        this.mealTemplateRepository = mealTemplateRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> getWeek(MealPlanGetWeekDto dto) {
        Map<String, Object> out = new HashMap<>();
        if (dto.getUserId() == null || dto.getWeekId() == null || dto.getWeekId().isBlank()) {
            out.put("status", "error");
            out.put("message", "userId and weekId are required");
            out.put("days", null);
            return out;
        }
        if (userRepository.findById(dto.getUserId()).isEmpty()) {
            out.put("status", "error");
            out.put("message", "User not found");
            out.put("days", null);
            return out;
        }
        Optional<UserMealWeekEntity> row = mealWeekRepository.findByUser_IdAndWeekKey(dto.getUserId(), dto.getWeekId().trim());
        if (row.isEmpty()) {
            out.put("status", "success");
            out.put("days", null);
            return out;
        }
        try {
            JsonNode root = objectMapper.readTree(row.get().getPlanJson());
            JsonNode days = root.get("days");
            out.put("status", "success");
            out.put("days", days != null && !days.isNull() ? days : null);
        } catch (Exception e) {
            out.put("status", "error");
            out.put("message", "Stored plan could not be read");
            out.put("days", null);
        }
        return out;
    }

    @Override
    @Transactional
    public Map<String, Object> saveWeek(MealPlanSaveWeekDto dto) {
        Map<String, Object> out = new HashMap<>();
        if (dto.getUserId() == null || dto.getWeekId() == null || dto.getWeekId().isBlank() || dto.getDays() == null
                || dto.getDays().isNull()) {
            out.put("status", "error");
            out.put("message", "userId, weekId, and days are required");
            return out;
        }
        Optional<UserEntity> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            out.put("status", "error");
            out.put("message", "User not found");
            return out;
        }
        String weekKey = dto.getWeekId().trim();
        ObjectNode plan = objectMapper.createObjectNode();
        plan.set("days", dto.getDays());

        UserMealWeekEntity entity = mealWeekRepository.findByUser_IdAndWeekKey(dto.getUserId(), weekKey).orElse(null);
        if (entity == null) {
            entity = new UserMealWeekEntity();
            entity.setUser(userOpt.get());
            entity.setWeekKey(weekKey);
        }
        entity.setPlanJson(plan.toString());
        mealWeekRepository.save(entity);
        out.put("status", "success");
        out.put("message", "Week saved");
        return out;
    }

    @Override
    public Map<String, Object> listTemplates(MealPlanUserOnlyDto dto) {
        Map<String, Object> out = new HashMap<>();
        if (dto.getUserId() == null) {
            out.put("status", "error");
            out.put("message", "userId is required");
            out.put("templates", List.of());
            return out;
        }
        if (userRepository.findById(dto.getUserId()).isEmpty()) {
            out.put("status", "error");
            out.put("message", "User not found");
            out.put("templates", List.of());
            return out;
        }
        List<UserMealTemplateEntity> rows = mealTemplateRepository.findByUser_IdOrderByNameAsc(dto.getUserId());
        List<Map<String, Object>> templates = new ArrayList<>();
        for (UserMealTemplateEntity row : rows) {
            Map<String, Object> one = new HashMap<>();
            one.put("name", row.getName());
            try {
                one.put("days", objectMapper.readTree(row.getDaysJson()));
            } catch (Exception e) {
                one.put("days", objectMapper.createArrayNode());
            }
            templates.add(one);
        }
        out.put("status", "success");
        out.put("templates", templates);
        return out;
    }

    @Override
    @Transactional
    public Map<String, Object> saveTemplates(MealPlanSaveTemplatesDto dto) {
        Map<String, Object> out = new HashMap<>();
        if (dto.getUserId() == null) {
            out.put("status", "error");
            out.put("message", "userId is required");
            return out;
        }
        Optional<UserEntity> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            out.put("status", "error");
            out.put("message", "User not found");
            return out;
        }
        Long userId = dto.getUserId();
        mealTemplateRepository.deleteByUser_Id(userId);

        List<MealPlanTemplateEntryDto> list = dto.getTemplates();
        if (list != null) {
            Map<String, MealPlanTemplateEntryDto> deduped = new LinkedHashMap<>();
            for (MealPlanTemplateEntryDto t : list) {
                if (t == null || t.getName() == null || t.getName().isBlank() || t.getDays() == null || t.getDays().isNull()) {
                    continue;
                }
                deduped.put(t.getName().trim().toLowerCase(), t);
            }
            for (MealPlanTemplateEntryDto t : deduped.values()) {
                UserMealTemplateEntity e = new UserMealTemplateEntity();
                e.setUser(userOpt.get());
                e.setName(t.getName().trim());
                e.setDaysJson(t.getDays().toString());
                mealTemplateRepository.save(e);
            }
        }
        out.put("status", "success");
        out.put("message", "Templates saved");
        return out;
    }
}
