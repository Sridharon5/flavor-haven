package com.flavorhaven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flavorhaven.entity.UserMealTemplateEntity;

public interface UserMealTemplateRepository extends JpaRepository<UserMealTemplateEntity, Long> {

    List<UserMealTemplateEntity> findByUser_IdOrderByNameAsc(Long userId);

    void deleteByUser_Id(Long userId);
}
