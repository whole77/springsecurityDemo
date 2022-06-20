package com.example.springsercurity2.config;

import com.example.springsercurity2.filter.KaptchaFilter;
import com.example.springsercurity2.handler.MyAuthenticationFailHandler;
import com.example.springsercurity2.handler.MyAuthenticationSuccessHandler;
import com.example.springsercurity2.handler.MyLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * @Author: LaiLai
 * @Date: 2022/06/16/22:37
 */
@EnableWebSecurity
@Configuration
public class springsecurityconfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    KaptchaFilter kaptchaFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.rememberMe().key("remember-me").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(120);
        http.
                addFilterBefore(kaptchaFilter,UsernamePasswordAuthenticationFilter.class)
            .formLogin()
           .loginPage("/dologin")
                .usernameParameter("username")
                .passwordParameter("password")
//               表单提交后这是post请求，转发后仍然是post请求，所以要么用postmapping来接受，要么直接重定向
           .defaultSuccessUrl("/user",true)
//                failureUrl 这时重定向 failureForwardUrl这是转发
           .failureUrl("/dologin?error=true")
           .and()
           .logout()
           .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
//                session失效时退出
           .invalidateHttpSession(true)
                //退出时清除认证信息
           .clearAuthentication(true)
//                前后段分离就返回json字符串，不分离就返回路径
           .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and()
                .authorizeRequests()
           .antMatchers( "/css/**","/js/**","/img/**","/fonts/**","/vc.jpg","/favicon.ico","/dologin").permitAll()
           .antMatchers("/index").hasRole("product")
           .anyRequest()
            .authenticated()
            .and()
            .csrf()
        .disable();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        // 将 DataSource 设置到 PersistentTokenRepository
        persistentTokenRepository.setDataSource(dataSource);
        // 第一次启动的时候自动建表（可以不用这句话，自己手动建表，源码中有语句的）
        // persistentTokenRepository.setCreateTableOnStartup(true);
        return persistentTokenRepository;
    }

}
