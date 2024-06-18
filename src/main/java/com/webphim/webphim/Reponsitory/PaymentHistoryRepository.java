package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Integer> {
}