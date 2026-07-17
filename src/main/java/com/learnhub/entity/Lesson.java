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
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String videoUrl;

    private Integer lessonOrder;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;
    @OneToMany(mappedBy = "lesson",    cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Quiz> quizzes;

    @OneToMany(
            mappedBy = "lesson",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<LessonProgress> lessonProgresses;
}
