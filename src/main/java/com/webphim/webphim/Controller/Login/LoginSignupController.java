/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller.Login;

import com.webphim.webphim.Model.PaymentHistory;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.RoleRepository;
import com.webphim.webphim.Service.CustomUserDetailsService;
import com.webphim.webphim.Service.EmailService;
import com.webphim.webphim.Service.PaymentHistoryService;
import com.webphim.webphim.Service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


/**
 *
 * @author Acer
 */
@Controller
public class LoginSignupController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PaymentHistoryService paymentHistoryService;
    @GetMapping("/Login_Signup")
    public String index() {
        return "Login_Signup/index";
    }
    @PostMapping("/Signup")
    public String registerUser(Model model, @RequestParam String username, @RequestParam String email, @RequestParam String phone, @RequestParam String password) {//String username,String email,String phone,String password
        Users checkEmail = usersService.findUserByEmail(email);
        if(checkEmail != null) {
            model.addAttribute("failMessage", "Email đã dăng ký!");
            return "Login_Signup/index";
        }
        Optional<Users> checkUserName = usersService.findUserByUsername(username);
        if(!checkUserName.isEmpty()) {
            model.addAttribute("failMessage", "Username đã tồn tại!");
            return "Login_Signup/index";
        }
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        usersService.saveUser(user);
        usersService.setDefaultRole(user.getUsername());
        model.addAttribute("successMessage", "Đăng ký thành công!");
        return "Login_Signup/index";
    }
    @GetMapping("/logout")
    public String performLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/Home";
    }
    @GetMapping("/ForgetPass")
    public String ShowAccuracyEmail() {
        return "Login_Signup/AccuracyEmail";
    }
    @PostMapping("/ForgetPass")
    public String AccuracyEmail(Model model, @RequestParam String email, HttpSession session) {
	    session.setAttribute("randomCode", emailService.generateRandomCode());
        session.setAttribute("emailResetPass", email);
        int randomCode = (int) session.getAttribute("randomCode");
        Users user = usersService.findUserByEmail(email);
        String body = "Chúng tôi đã nhận được yêu cầu thay đổi mật khẩu từ bạn. Để hoàn tất quá trình này, vui lòng sử dụng mã xác thực dưới đây:\n" +
                " \n" +
                "Mã xác thực của bạn: "+ randomCode +"\n" +
                " \n" +
                "Vui lòng làm theo các bước sau để thay đổi mật khẩu của bạn:\n" +
                " \n" +
                "Truy cập trang thay đổi mật khẩu: Thay đổi mật khẩu (liên kết này sẽ đưa bạn đến trang thay đổi mật khẩu của chúng tôi).\n" +
                "Nhập mã xác thực: Vui lòng nhập mã xác thực mà bạn nhận được vào ô yêu cầu.\n" +
                "Tạo mật khẩu mới: Sau khi nhập mã xác thực, bạn sẽ được yêu cầu tạo một mật khẩu mới. Hãy chắc chắn rằng mật khẩu mới của bạn an toàn và dễ nhớ.\n" +
                "Nếu bạn gặp bất kỳ khó khăn nào trong quá trình này, vui lòng liên hệ với bộ phận hỗ trợ khách hàng của chúng tôi tại địa chỉ [hngdat2003@gmail.com] hoặc số điện thoại [0934897354].\n" +
                " \n" +
                "Chúng tôi xin lỗi vì sự bất tiện này và rất mong sớm giải quyết được vấn đề của bạn.\n" +
                " \n" +
                "Trân trọng,\n" +
                " \n" +
                 "Nhóm 3."+"\n";
        if(user != null) {
            emailService.sendEmail(email,"Xác Thực Email",body);
            return "Login_Signup/confirmCode";
        } else {
            model.addAttribute("messageError", "Email Không tồn tại!");
            return "Login_Signup/AccuracyEmail";
        }
    }
    @PostMapping("/ForgetPass/ConfirmCode")
    public String ShowConfirmCode(Model model, @RequestParam int code, HttpSession session) {
	    int randomCode = (int) session.getAttribute("randomCode");
        if(code == randomCode) {
            return "Login_Signup/RepeatPass";
        } else {
            model.addAttribute("messageError", "Code không hợp lệ!");
            return "Login_Signup/confirmCode";
        }
    }
    @PostMapping("/ForgetPass/ResetPass")
    public String ShowResetPass(Model model, @RequestParam String password, HttpSession session) {
        String email = (String) session.getAttribute("emailResetPass");
        Users user = usersService.findUserByEmail(email);
        usersService.setPassUserById(user.getId(), password);
        session.removeAttribute("emailResetPass");
        session.removeAttribute("randomCode");
        model.addAttribute("successMessage", "Reset Password Thành Công!");
        return "redirect:/Login_Signup";
    }
}
