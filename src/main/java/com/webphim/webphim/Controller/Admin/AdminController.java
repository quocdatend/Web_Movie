package com.webphim.webphim.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @RequestMapping("/Admin")
    public String index(){
        return "Admin/index";
    }

}
