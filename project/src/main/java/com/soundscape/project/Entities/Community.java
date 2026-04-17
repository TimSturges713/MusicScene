package com.soundscape.project.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "community")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float lat;

    private float lng;

    private String name;

    private Long radius;

    private Long population;

    protected Community(){

    }

    public Community(String name, float lat, float lng, Long radius, Long population){
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
        this.population = population;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public float getLat() { return lat; }
    public void setLat(float lat) { this.lat = lat; }

    public float getLng() { return lng; }
    public void setLng(float lng) { this.lng = lng; }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Long getRadius() { return radius; }
    public void setRadius(Long radius) { this.radius = radius; }

    public Long getPopulation() { return population; }
    public void setPopulation(Long population) { this.population = population; }
}
