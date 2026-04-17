package com.soundscape.project.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "communities")
public class Community {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lat;

    private double lng;

    private String name;

    private float radius;

    private Long population;

    protected Community(){

    }

    public Community(String name, float lat, float lng, float radius, Long population){
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
        this.population = population;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public float getRadius() { return radius; }
    public void setRadius(float radius) { this.radius = radius; }

    public Long getPopulation() { return population; }
    public void setPopulation(Long population) { this.population = population; }
}
