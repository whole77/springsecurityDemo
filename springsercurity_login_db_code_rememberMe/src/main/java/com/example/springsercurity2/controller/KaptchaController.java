package com.example.springsercurity2.controller;

import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class KaptchaController {
private final Producer producer;
@Autowired
RedisTemplate<String,Object> redisTemplate;
@Autowired
public KaptchaController(Producer producer) {
this.producer = producer;
 }
@GetMapping("/vc.jpg")
public void getVerifyCode(HttpServletResponse response, HttpSession session) throws IOException {
          response.setContentType("image/png");
          String code = producer.createText();
          redisTemplate.opsForValue().set("kaptcha",code,60, TimeUnit.SECONDS);
          log.info((String)redisTemplate.opsForValue().get("kaptcha"));
          BufferedImage bi = producer.createImage(code);
          ServletOutputStream os =
          response.getOutputStream();
          ImageIO.write(bi, "jpg", os);
 }
}