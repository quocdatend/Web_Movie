package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "PAYMENT_HISTORY")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String price;
    @Column(nullable = false)
    private LocalDateTime time;
    @Column(nullable = false)
    private LocalDateTime timeOver;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private boolean duration = true;
    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;
}
