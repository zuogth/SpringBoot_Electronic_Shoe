package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity,Long> {

    List<EventEntity> findByShowWebAndEndAtAfter(Integer showWeb, LocalDateTime dateTime);
    Optional<EventEntity> findBySlug(String slug);

    List<EventEntity> findByEndAtAfter(LocalDateTime dateTime);
}
