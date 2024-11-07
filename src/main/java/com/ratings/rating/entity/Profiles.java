package com.ratings.rating.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Profiles {
    @Id
    public String username;
    public String name;
    
    public String cc_id;
    public String cf_id;
    public String leet_id;

    // Getters and setters
}
