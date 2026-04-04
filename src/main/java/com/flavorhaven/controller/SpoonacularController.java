package com.flavorhaven.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/spoonacular")
@CrossOrigin(origins = "*")
public class SpoonacularController {

    @Value("${spoonacular.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping({ "/recipes/complexSearch", "/complexSearch" })
    public ResponseEntity<String> complexSearch(HttpServletRequest request) {
        MultiValueMap<String, String> params = forwardParamsWithoutApiKey(request);
        params.add("apiKey", apiKey);

        String url = UriComponentsBuilder
                .fromUriString("https://api.spoonacular.com/recipes/complexSearch")
                .queryParams(params)
                .build()
                .toUriString();

        String body = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(body);
    }

    @GetMapping({ "/recipes/{id}/information", "/{id}/information" })
    public ResponseEntity<String> getRecipeInfo(@PathVariable String id, HttpServletRequest request) {
        MultiValueMap<String, String> params = forwardParamsWithoutApiKey(request);
        if (!params.containsKey("includeNutrition")) {
            params.add("includeNutrition", "false");
        }
        params.add("apiKey", apiKey);

        String url = UriComponentsBuilder
                .fromUriString("https://api.spoonacular.com/recipes/{id}/information")
                .queryParams(params)
                .buildAndExpand(id)
                .toUriString();

        String body = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(body);
    }

    private static MultiValueMap<String, String> forwardParamsWithoutApiKey(HttpServletRequest request) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Collections.list(request.getParameterNames()).forEach(name -> {
            if ("apiKey".equalsIgnoreCase(name)) {
                return;
            }
            for (String value : request.getParameterValues(name)) {
                params.add(name, value);
            }
        });
        return params;
    }
}
