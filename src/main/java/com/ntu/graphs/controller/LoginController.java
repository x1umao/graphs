package com.ntu.graphs.controller;

import com.ntu.graphs.entity.User;
import com.ntu.graphs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    @ResponseBody
    public Map<String, Integer> logout(HttpServletRequest request) {

        Map<String, Integer> resData = new HashMap<>();
        resData.put("msg", userService.logout(request));
        return resData;
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public Map<String, Integer> login(@ModelAttribute User user, HttpServletRequest request) {
        Map<String, Integer> resData = new HashMap<>();
        resData.put("msg", userService.verifyUser(user, request));
        return resData;
    }

    @GetMapping("/key")
    @ResponseBody
    public String getKey(@RequestParam("username") String username) {
        return userService.getKey(username);
    }
}
