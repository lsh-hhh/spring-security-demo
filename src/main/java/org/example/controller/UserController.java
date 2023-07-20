package org.example.controller;

import org.example.common.Result;
import org.example.model.User;
import org.example.service.UserService;
import org.example.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserLogin
 * @Author lsh
 * @Date 2023/7/17 15:24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('login')")
    @GetMapping("/info")
    public Result<UserVO> get() {
        return Result.success(userService.getInfo());
    }
}
