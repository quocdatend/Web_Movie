package com.webphim.webphim.Service;

import com.webphim.webphim.Model.PaymentHistory;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.PaymentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentHistoryService {
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    private UsersService usersService;
    public void save(PaymentHistory paymentHistory) {paymentHistoryRepository.save(paymentHistory);}
    public void updateDuration(Long id) {}
    public void getIfDurationIsTrue(Long id, Users users) {
        List<PaymentHistory> paymentHistoryList = paymentHistoryRepository.findByUsersId(id);
        System.out.println(true);
        for (PaymentHistory paymentHistory: paymentHistoryList) {
            if (paymentHistory.isDuration()) {
                if(paymentHistory.getTime().isAfter(paymentHistory.getTimeOver())) {
                    paymentHistory.setDuration(false);
                    this.save(paymentHistory);
                    usersService.deletePremiumRole(users.getUsername());
                }
            }
        }
    }

    public List<PaymentHistory> getByUserId(Long id) {return paymentHistoryRepository.findByUsersId(id);}
}
