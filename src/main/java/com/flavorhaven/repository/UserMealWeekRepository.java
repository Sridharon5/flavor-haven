package com.flavorhaven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flavorhaven.entity.UserMealWeekEntity;

public interface UserMealWeekRepository extends JpaRepository<UserMealWeekEntity, Long> {

    Optional<UserMealWeekEntity> findByUser_IdAndWeekKey(Long userId, String weekKey);

    void deleteByUser_IdAndWeekKey(Long userId, String weekKey);
}
