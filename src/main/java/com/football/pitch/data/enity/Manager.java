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
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String fullName;

    @Column(name = "phone_number", columnDefinition = "CHAR(11)", nullable = false)
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_pitch")
    private int totalPitch;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private BigDecimal basic_salary;
}
