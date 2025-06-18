package com.disaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.disaster.entities.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Query(value = "SELECT * FROM resource WHERE ST_Distance_Sphere(location, ST_GeomFromText(:point, 4326)) <= :radius", nativeQuery = true)
    List<Resource> findNearby(@Param("point") String pointWKT, @Param("radius") double radius);
}