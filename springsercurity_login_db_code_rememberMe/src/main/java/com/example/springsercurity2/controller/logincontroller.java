package com.example.springsercurity2.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: LaiLai
 * @Date: 2022/06/17/0:02
 */
@Controller
public class logincontroller {

    @RequestMapping("/dologin")
    public String login(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(){
        return "index";
    }
    @RequestMapping("/index")
    public String index(){
        return "redirect:/index";
    }

    @GetMapping("/user")
    public String user(){
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        System.out.println("身份 :"+principal.getUsername());
        System.out.println("凭证:"+authentication.getCredentials());
        System.out.println("权限:"+authentication.getAuthorities());
        return "user";
    }

}
