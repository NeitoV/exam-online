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

    @Column(name = "answer_first", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String answerFirst;

    @Column(name = "answer_second", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String answerSecond;

    @Column(name = "answer_third", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String answerThird;

    @Column(name = "answer_fourth", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String answerFourth;

    @Column(name = "correct_answer", columnDefinition = "char(1)", nullable = false)
    private String correctAnswer;

}