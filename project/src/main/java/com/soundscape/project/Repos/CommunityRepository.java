package com.soundscape.project.Repos;

import com.soundscape.project.Entities.Community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("SELECT c FROM Community c WHERE ABS(c.lat - :lat) < 0.01 AND ABS(c.lng - :lng) < 0.01")
    Community findNearLatLng(@Param("lat") double lat, @Param("lng") double lng);

    Community findByName(@Param("name") String name);
}