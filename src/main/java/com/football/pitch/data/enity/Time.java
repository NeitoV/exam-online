package com.football.pitch.data.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start", columnDefinition = "NVARCHAR(25)", nullable = false)
    private String start;

    @Column(name = "end", columnDefinition = "NVARCHAR(25)", nullable = false)
    private String end;
}
