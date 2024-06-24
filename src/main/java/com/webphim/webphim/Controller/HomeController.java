/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Map;

/**
 *
 * @author Acer
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping(value = { "",  "/", "/Home" })
    public String index(){
        return "Home/index";
    }

}
