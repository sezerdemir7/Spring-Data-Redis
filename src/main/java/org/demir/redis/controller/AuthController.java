package org.demir.redis.controller;


import org.demir.redis.service.VerificationCodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final VerificationCodeService verificationCodeService;

    public AuthController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId) {
        // authentication processes

        String code = verificationCodeService.generateVerificationCode(userId);
        System.out.println("Verification code: " + code);

        return "Verification code sent";
    }

    @PostMapping("/verify")
    public String verify(@RequestParam String userId, @RequestParam String code) {
        boolean isVerified = verificationCodeService.verifyCode(userId, code);
        if (isVerified) {
            return "Login successful";
        } else {
            return "Invalid verification code";
        }
    }
}

