package com.ntu.graphs.controller;

import com.ntu.graphs.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @GetMapping("/admin-dash")
    public String adminDash(Model model, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("username",user.getUsername());
        return "admin-dash";
    }

}
