package com.IntelligentAssistant.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author thpaperman
 * @Description 验证码配置类
 * @Date 2026/1/20
 * @DAY_NAME_FULL: 星期二
 * @Version 1.0
 */

@Configuration
public class CaptchaConfig {

    /**
     * 验证码生成器
     *
     * @return {@link DefaultKaptcha}
     */
    @Bean
    public DefaultKaptcha captchaProducer() {
        Properties props = new Properties();
        props.setProperty("kaptcha.image.width", "160");
        props.setProperty("kaptcha.image.height", "60");
        props.setProperty("kaptcha.textproducer.char.length", "5");
        props.setProperty("kaptcha.textproducer.font.size", "45");
        props.setProperty("kaptcha.textproducer.char.string", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        Config config = new Config(props);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
