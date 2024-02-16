package com.football.pitch.data.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name", columnDefinition = "NVARCHAR(250)", nullable = false)
    private String fullName;

    @Column(name = "phone_number", columnDefinition = "CHAR(11)", nullable = false)
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer belief = 100;
}
