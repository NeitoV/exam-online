package com.exam.data.enity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", columnDefinition = "NVARCHAR(25)", nullable = false, unique = true)
    private String code;

    @Column(name = "name", columnDefinition = "NVARCHAR(25)", nullable = false, unique = true)
    private String name;
}