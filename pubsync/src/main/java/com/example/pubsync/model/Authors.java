package com.example.pubsync.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "authors")
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public void setName(String s) {
    }
    public void setLastName(String s) {
    }

    public void setActive(boolean b) {
    }



}