package org.demir.redis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {


    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CODE_PREFIX = "verification_code:";
    private static final long CODE_EXPIRE_TIME = 5;

    public VerificationCodeService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateVerificationCode(String userId) {
        String code = String.format("%06d", new Random().nextInt(999999));
        String key = CODE_PREFIX + userId;
        redisTemplate.opsForValue().set(key, code, CODE_EXPIRE_TIME, TimeUnit.MINUTES);
        return code;
    }

    public boolean verifyCode(String userId, String code) {
        String key = CODE_PREFIX + userId;
        String savedCode = (String) redisTemplate.opsForValue().get(key);
        return code.equals(savedCode);
    }
}
