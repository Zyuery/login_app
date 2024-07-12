package org.example.login_app.controller;


import org.example.login_app.domain.User;
import org.example.login_app.service.UserService;
import org.example.login_app.service.servicelmpl.EmailService;
import org.example.login_app.service.servicelmpl.VerificationCodeService;
import org.example.login_app.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import  jakarta.servlet.http.*;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/login")
    public Result loginController(@RequestParam String uname, @RequestParam String password,
                                  @RequestParam(required = false) String captcha, HttpSession session) {
        Integer errorCount = (Integer) session.getAttribute("errorCount");
        if (errorCount == null) {errorCount = 0;}
        // 如果错误次数大于等于阈值，验证验证码
        if (errorCount >= 1)
        {
            String expectedCaptcha = (String) session.getAttribute("kaptcha");
            session.removeAttribute("kaptcha"); // 使用后删除 session 中的验证码属性
            if (expectedCaptcha == null || !captcha.equalsIgnoreCase(expectedCaptcha))
            {
                errorCount++;
                session.setAttribute("errorCount",errorCount);
                return Result.error("-3", "验证码错误！",errorCount);
            }
        }
        // 执行登录认证
        User user = userService.loginService(uname, password);
        // 根据登录结果返回适当的响应
        if (user != null)
        {
            // 登录成功，重置错误次数
            session.setAttribute("errorCount", 0);
            return Result.success(user, "登录成功",0);
        }
        else
        {
            // 登录失败，增加错误次数
            errorCount++;
            session.setAttribute("errorCount",errorCount);
            return Result.error("-1", "账号或密码错误！",errorCount);
        }
    }

    @PostMapping("/register")
    public Result<User> registController(@RequestBody User newUser) {
        // 执行用户注册
        User user = userService.registService(newUser);

        // 根据注册结果返回适当的响应
        if (user != null) {
            return Result.success(user, "注册成功！",0);
        } else {
            return Result.error("-2", "用户名已存在！",1);
        }
    }
    @PostMapping("/login_email")
    public Result<User> loginWithEmailVerification(@RequestParam String email, @RequestParam String verificationCode,
                                                   @RequestParam(required = false) String captcha, HttpSession session) {
        Integer errorCount = (Integer) session.getAttribute("errorCount");
        if (errorCount == null) {errorCount = 0;}
        // 如果错误次数大于等于阈值，验证验证码
        if (errorCount >= 1)
        {
            String expectedCaptcha = (String) session.getAttribute("kaptcha");
            session.removeAttribute("kaptcha"); // 使用后删除 session 中的验证码属性
            if (expectedCaptcha == null || !captcha.equalsIgnoreCase(expectedCaptcha))
            {
                errorCount++;
                session.setAttribute("errorCount",errorCount);
                return Result.error("-3", "验证码错误！",errorCount);
            }
        }
        User user = userService.emailLoginService(email);
        if(user != null)
        {
            //验证邮箱验证码
            if (verificationCodeService.verify(email, verificationCode)) {
                // 登录成功，重置错误次数
                return Result.success(user, "登录成功",0);
            }
            else
            {
                errorCount++;
                session.setAttribute("errorCount",errorCount);
                return Result.error("-4", "邮箱验证失败",errorCount);
            }
        }
        else
        {
            errorCount++;
            session.setAttribute("errorCount",errorCount);
            return Result.error("-5", "邮箱异常",errorCount);
        }
    }
    }