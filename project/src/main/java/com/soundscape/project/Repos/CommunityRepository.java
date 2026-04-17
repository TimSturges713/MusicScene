package com.soundscape.project.Repos;

import java.util.*;

import com.soundscape.project.Entities.Community;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Community findByLatLng(float lat, float lng);
}