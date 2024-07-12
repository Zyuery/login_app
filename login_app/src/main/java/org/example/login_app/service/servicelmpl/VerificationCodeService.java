package org.example.login_app.service.servicelmpl;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationCodeService {

    private Map<String, String> verificationMap = new HashMap<>();

    public String generateVerificationCode() {
        // Generate a random 6-digit verification code
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        return code;
    }

    public void saveVerificationCode(String email, String code) {
        verificationMap.put(email, code);
    }

    public boolean verify(String email, String code) {
        String savedCode = verificationMap.get(email);
        return savedCode != null && savedCode.equals(code);
    }
}