package com.example.springsercurity2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LaiLai
 * @Date: 2022/06/19/15:00
 */
public class MyAuthExceptionHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
          Map<String,Object> map = new HashMap<>();
          map.put("msg","系统出错啦!");
          map.put("status",500);
          String s = new ObjectMapper().writeValueAsString(map);
          response.getWriter().println(s);
    }
}
