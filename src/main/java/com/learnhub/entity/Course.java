package com.learnhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name="instructor_id")
    private User instructor;
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Enrollment> enrollments;
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Lesson> lessons;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "instructor_id")
//    private Instructor instructor;
@OneToMany(
        mappedBy = "course",
        cascade = CascadeType.ALL,
        orphanRemoval = true
)
@JsonIgnore
private List<Certificate> certificates;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Progress> progresses;

}