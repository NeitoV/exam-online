package com.exam.data.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExamResultDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "selected_answer", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String selectedAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_results_id", nullable = false)
    private ExamResult examResult;
}
