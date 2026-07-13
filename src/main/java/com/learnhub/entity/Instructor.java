package com.learnhub.entity;

import jakarta.persistence.*;

import java.util.List;

public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;

    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    private List<Course> courses;
}
