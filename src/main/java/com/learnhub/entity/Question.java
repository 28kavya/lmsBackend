package com.learnhub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonBackReference
    private Quiz quiz;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<StudentAnswer> studentAnswers;
}