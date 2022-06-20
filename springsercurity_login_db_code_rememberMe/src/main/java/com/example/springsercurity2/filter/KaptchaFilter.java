package com.example.springsercurity2.filter;//package com.example.springsercurity2.filter;

import com.example.springsercurity2.exception.KaptchaNotMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @Author: LaiLai
 * @Date: 2022/06/18/22:29
 */
@Slf4j
@Component("kaptchaFilter")
public class KaptchaFilter extends OncePerRequestFilter{
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/dologin", request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
            if (!validate((request))){
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,new KaptchaNotMatchException("验证码输入错误"));
                request.getRequestDispatcher("/dologin?error=true").forward(request,response);
            }else{
               filterChain.doFilter(request,response);
            }
        }else{
            filterChain.doFilter(request,response);
        }
    }
    public  boolean validate(HttpServletRequest request){
        String kaptcha = request.getParameter("kaptcha");
        if(StringUtils.isEmpty(kaptcha)){
            request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new KaptchaNotMatchException("验证码为空"));
            return false;
        }
        Long kaptcha1 = redisTemplate.opsForValue().getOperations().getExpire("kaptcha");

        //返回-1 表示没有设置过期时间   返回-2表示没有该值
        if(kaptcha1<0||kaptcha1==-2){
            request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new KaptchaNotMatchException("验证码已过期"));
            return false;
        }
        String kaptcha2 =(String)redisTemplate.opsForValue().get("kaptcha");
        if(!StringUtils.equalsIgnoreCase(kaptcha2,kaptcha)){
            request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new KaptchaNotMatchException("验证码错误"));
            return false;
        }
        return true;
    }

}
