package com.example.springsercurity2.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author: LaiLai
 * @Date: 2022/06/18/22:21
 */
@Configuration
public class kaptchaConfig  {
    @Bean
    public DefaultKaptcha kaptchaConfiguration(){
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "100");//宽
        properties.setProperty("kaptcha.image.height", "40");//高
        properties.setProperty("kaptcha.textproducer.font.size", "32");//字体大小
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");//颜色
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ");//字符集
        properties.setProperty("kaptcha.textproducer.char.length", "4");//生成的验证码字符个数
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");//干扰项
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
