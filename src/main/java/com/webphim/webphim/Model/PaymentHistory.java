package com.webphim.webphim.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "PAYMENT_HISTORY")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payId;

    @Column(nullable = false, length = 20)
    private String bank;

    @Column(nullable = false)
    private Time time;

    @Column(nullable = false)
    private float money;

    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;

    // Getters and setters
}
