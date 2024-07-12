package org.example.login_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class ViewController {
        // GET 方法返回登录页面
        @GetMapping("/login")
        public String loginPage(Model model) {
            // 可以向模板传递需要的数据
            model.addAttribute("pageTitle", "登录页面");
            return "login"; // 这里返回的是Thymeleaf模板的文件名，比如 login.html
        }
        @GetMapping("/login_email")
        public String login_emailPage(Model model) {
            // 可以向模板传递需要的数据
            model.addAttribute("pageTitle", "登录页面");
            return "login_email"; // 这里返回的是Thymeleaf模板的文件名，比如 login_email.html
        }
        // GET 方法返回注册页面
        @GetMapping("/register")
        public String registerPage(Model model) {
            // 可以向模板传递需要的数据
            model.addAttribute("pageTitle", "注册页面");
            return "register"; // 这里返回的是Thymeleaf模板的文件名，比如 register.html
        }
        @GetMapping("/home")
         public String homePage(Model model) {
            // 可以向模板传递需要的数据
            model.addAttribute("pageTitle", "主页页面");
            return "home"; // 这里返回的是Thymeleaf模板的文件名，比如 register.html
        }
}


