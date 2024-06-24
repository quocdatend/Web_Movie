package com.webphim.webphim.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    private final Random random = new Random();
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    public int generateRandomCode() {
        int min = 1000;
        int max = 9999;
        return random.nextInt(max - min + 1) + min;
    }
    public void editAccountAdmin(String email, String username) {
        String subject = "Thông Tin Tài khoản Admin Web xem phim";
        String body = "Thông tin tài khoản Admin\n" +
                    "Username :" + username + "\n" +
                    "Email: " + email + "\n" +
                    "Vui lòng truy cập đường link sau " + "http://localhost:8090/Login_Signup" + " để đăng nhập lại trang web.\n" +
                    "Trân Trọng.";
        this.sendEmail(email,subject,body);
    }
}
