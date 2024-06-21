package com.webphim.webphim.Controller.Admin;

import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private UsersService usersService;
    @GetMapping("")
    public String index(){
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
}
