package com.IntelligentAssistant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class IntelligentAssistantApplicationTests {

    @Test
    void contextLoads() {
        // 获取加密后的密码
        System.out.println(new BCryptPasswordEncoder().encode("1234"));
    }

}
