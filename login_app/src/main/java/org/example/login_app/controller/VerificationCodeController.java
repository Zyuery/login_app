package org.example.login_app.controller;

import org.example.login_app.service.servicelmpl.EmailService;
import org.example.login_app.service.servicelmpl.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/sendVerificationCode")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        try {
            // 生成邮箱验证码
            String code = verificationCodeService.generateVerificationCode();

            // 保存验证码
            verificationCodeService.saveVerificationCode(email, code);

            // 发送邮箱验证码
            emailService.sendVerificationCode(email, code);

            return ResponseEntity.ok("Verification code sent successfully");
        } catch (Exception e) {
            // 记录异常信息到日志
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send verification code: " + e.getMessage());
        }
    }
}
