package com.webphim.webphim.Controller.Admin;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Service.AdminMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {
    @RequestMapping("/Admin")
    public String index(){
        return "Admin/Index";
    }

}
