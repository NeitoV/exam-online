package com.exam.data.enity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(name = "content", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String content;

    @Column(name = "first_answer", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String firstAnswer;

    @Column(name = "second_answer", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String secondAnswer;

    @Column(name = "third_answer", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String thirdAnswer;

    @Column(name = "fourth_answer", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String fourthAnswer;

    @Column(name = "correct_answer", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String correctAnswer;

}