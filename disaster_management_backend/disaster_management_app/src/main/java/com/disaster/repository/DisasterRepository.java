package com.disaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disaster.entities.Disaster;

public interface DisasterRepository extends JpaRepository<Disaster, Long> {
    List<Disaster> findByTagsContaining(String tag);
}
