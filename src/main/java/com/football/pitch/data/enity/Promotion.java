package com.football.pitch.data.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "NVARCHAR(25)", nullable = false, unique = true)
    private String name;

    @Column(name = "discount_percentage", nullable = false)
    private Float discountPercentage;

    @Column(name = "max_get", nullable = false)
    private BigDecimal maxGet;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
}
