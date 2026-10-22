package com.course.learning.controller;

import com.course.learning.common.Result;
import com.course.learning.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户认证与个人信息
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** 账号密码注册 */
    @PostMapping("/register")
    public Result<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        return Result.ok("注册成功", userService.register(
                body.get("username"), body.get("password"), body.get("nickname")));
    }

    /** 账号密码登录 */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        return Result.ok("登录成功", userService.login(body.get("username"), body.get("password")));
    }

    /** 微信小程序登录（wx_openid 静默注册/登录） */
    @PostMapping("/wx-login")
    public Result<Map<String, Object>> wxLogin(@RequestBody Map<String, String> body) {
        return Result.ok("登录成功", userService.wxLogin(body.get("wxOpenid"), body.get("nickname")));
    }

    /** 获取当前用户信息 */
    @GetMapping("/info")
    public Result<Map<String, Object>> info(@RequestAttribute("userId") Long userId) {
        return Result.ok(userService.getUserInfo(userId));
    }

    /** 绑定班级 */
    @PostMapping("/bind-class")
    public Result<Void> bindClass(@RequestAttribute("userId") Long userId,
                                  @RequestBody Map<String, Long> body) {
        userService.bindClass(userId, body.get("classId"));
        return Result.ok();
    }
}
