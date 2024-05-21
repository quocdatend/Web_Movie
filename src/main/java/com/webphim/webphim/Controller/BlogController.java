/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Acer
 */
@Controller
@RequestMapping("/Blog")
public class BlogController {
    
    @GetMapping("")
    public String index() {
        return "Blog/blog";
    }
    
    @GetMapping("/blog-details")
    public String blog_detail() {
        return "Blog/blog-details";
    }
    
    @GetMapping("/celebrities")
    public String Celebrities() {
        return "Blog/celebrities";
    }
}
