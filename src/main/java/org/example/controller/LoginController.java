package org.example.controller;

import org.example.common.Result;
import org.example.model.User;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserLogin
 * @Author lsh
 * @Date 2023/7/17 15:24
 */
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PreAuthorize("hasAuthority('config:list')")
    @GetMapping("/hello")
    public String hello(String name) {
        return "hello " + name;
    }

    @PostMapping("/user/login")
    public Result<String> login(@RequestBody User user) {
        return Result.success(loginService.login(user));
    }

    @PostMapping("/user/logout")
    public Result<String> logout() {
        loginService.logout();
        return Result.success("注销成功");
    }
}
