package com.webphim.webphim.Controller.Admin;

import com.webphim.webphim.Model.Admin;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.AdminRepository;
import com.webphim.webphim.Service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AdminMoviesService adminMoviesService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PreMovieService preMovieService;
    @Autowired
    private CommentsMovieService commentsMovieService;
    @Autowired
    private CommentLevelService commentLevelService;
    @GetMapping("")
    public String index(Model model){
        int totalMovie = adminMoviesService.getAllMovies().size();
        int totalAccount = usersService.GetAllNoAtt().size();
        int totalPreMovie = preMovieService.GetAllNoAtt().size();
        int totalCategory = categoryService.getAllCategories().size();
        int totalComment = commentsMovieService.getAll().size() + commentLevelService.getAll().size();
        model.addAttribute("totalMovie", totalMovie);
        model.addAttribute("totalAccount", totalAccount);
        model.addAttribute("totalPreMovie", totalPreMovie);
        model.addAttribute("totalCategory", totalCategory);
        model.addAttribute("totalComment", totalComment);
        return "Admin/index";
    }
    @GetMapping("/Account")
    public String showUsers(Model model,@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {
        Page<Users> users = usersService.GetAll(page,size);
        model.addAttribute("listUser", users);
        model.addAttribute("totalPages", users.getTotalPages());
        return "Admin/User/Account";
    }
    @GetMapping("Account/Delete/{id}")
    public String deleteUser(@PathVariable int id) {
        usersService.deleteUserById(id);
        return "redirect:/Admin/Account";
    }
    @GetMapping("/Account/Add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new Users());
        return "Admin/User/AddUser";
    }
    @PostMapping("/Account/Add")
    public String addUser(Model model, @RequestParam String username, @RequestParam String email, @RequestParam String phone, @RequestParam String password) {
        Users checkEmail = usersService.findUserByEmail(email);
        if(checkEmail != null) {
            model.addAttribute("failMessage", "Email đã dăng ký!");
            return "Admin/User/AddUser";
        }
        Optional<Users> checkUserName = usersService.findUserByUsername(username);
        if(!checkUserName.isEmpty()) {
            model.addAttribute("failMessage", "Username đã tồn tại!");
            return "Admin/User/AddUser";
        }
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        usersService.saveUser(user);
        usersService.setDefaultRole(user.getUsername());
        model.addAttribute("successMessage", "Đăng ký thành công!");
        return "Admin/User/AddUser";
    }
    @GetMapping("/Profile")
    public String showProfileAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Admin admin = adminService.getAdminByUsername(userDetails.getUsername());
        model.addAttribute("AdminProfile", admin);
        return "Admin/Profile/index";
    }
    @PostMapping("/Profile")
    public String editProfileAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails,
                                   @RequestParam String username, @RequestParam String email) {
        Admin adminSession = adminService.getAdminByUsername(userDetails.getUsername());
        Admin checkEmail = adminService.getAdminByEmail(email);
        Admin checkUsername = adminService.getAdminByUsername(username);
        // Trùng username và email ban đầu
        if(checkUsername != null && checkEmail != null) {
            if(checkUsername.getId() == adminSession.getId() && checkEmail.getId() == adminSession.getId()) {
                Admin admin = adminService.getAdminByUsername(userDetails.getUsername());
                model.addAttribute("AdminProfile", admin);
                return "Admin/Profile/index";
            }
            if(checkEmail.getId() != adminSession.getId() && checkUsername.getId() != adminSession.getId()) {
                    Admin admin = adminService.getAdminByUsername(userDetails.getUsername());
                    model.addAttribute("failMessage", "Username và Email đã tồn tại!");
                    model.addAttribute("AdminProfile", admin);
                    return "Admin/Profile/index";
            }
            if(checkEmail.getId() == adminSession.getId() && checkUsername.getId() != adminSession.getId()) {
                Admin admin = adminService.getAdminByUsername(userDetails.getUsername());
                model.addAttribute("failMessage", "Username đã tồn tại!");
                model.addAttribute("AdminProfile", admin);
                return "Admin/Profile/index";
            }
            if(checkEmail.getId() != adminSession.getId() && checkUsername.getId() == adminSession.getId()) {
                Admin admin = adminService.getAdminByUsername(userDetails.getUsername());
                model.addAttribute("failMessage", "Email đã tồn tại!");
                model.addAttribute("AdminProfile", admin);
                return "Admin/Profile/index";
            }
        }
        if(checkUsername == null && checkEmail != null) {
            if(checkEmail.getId() == adminSession.getId()) {
                adminService.setAdminByIdUsername(adminSession.getId(), username);
                emailService.editAccountAdmin(adminSession.getEmail(),username);
                Admin admin = adminService.getAdminByUsername(username);
                model.addAttribute("successMessage", "Lưu thay đổi thành công");
                model.addAttribute("AdminProfile", admin);
                model.addAttribute("backToLogin", "Đăng nhập lại để tiếp tục!");
            } else {
                Admin admin = adminService.getAdminByUsername(userDetails.getUsername());
                model.addAttribute("failMessage", "Email đã tồn tại!");
                model.addAttribute("AdminProfile", admin);
            }
            return "Admin/Profile/index";
        }
        if(checkUsername != null && checkEmail == null) {
            if(checkUsername.getId() == adminSession.getId()) {
                adminService.setAdminByIdEmail(adminSession.getId(), email);
                emailService.editAccountAdmin(email, adminSession.getUsername());
                Admin admin = adminService.getAdminByUsername(checkUsername.getUsername());
                model.addAttribute("successMessage", "Lưu thay đổi thành công");
                model.addAttribute("AdminProfile", admin);
                model.addAttribute("backToLogin", "Đăng nhập lại để tiếp tục!");
            } else {
                Admin admin = adminService.getAdminByUsername(userDetails.getUsername());
                model.addAttribute("failMessage", "Username đã tồn tại!");
                model.addAttribute("AdminProfile", admin);
            }
            return "Admin/Profile/index";
        }
        adminService.setAdminByIdUsernameEmail(adminSession.getId(), username, email);
        emailService.editAccountAdmin(email, username);
        Admin admin = adminService.getAdminByUsername(username);
        model.addAttribute("successMessage", "Lưu thay đổi thành công");
        model.addAttribute("AdminProfile", admin);
        model.addAttribute("backToLogin", "Đăng nhập lại để tiếp tục!");
        return "Admin/Profile/index";
    }

}
