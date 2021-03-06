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
//               ?????????????????????post???????????????????????????post????????????????????????postmapping?????????????????????????????????
           .defaultSuccessUrl("/user",true)
//                failureUrl ??????????????? failureForwardUrl????????????
           .failureUrl("/dologin?error=true")
           .and()
           .logout()
           .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
//                session???????????????
           .invalidateHttpSession(true)
                //???????????????????????????
           .clearAuthentication(true)
//                ????????????????????????json????????????????????????????????????
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
        // ??? DataSource ????????? PersistentTokenRepository
        persistentTokenRepository.setDataSource(dataSource);
        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
        // persistentTokenRepository.setCreateTableOnStartup(true);
        return persistentTokenRepository;
    }

}
