package com.example.springsercurity2.config;

import com.example.springsercurity2.dao.UserDao;
import com.example.springsercurity2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @Author: LaiLai
 * @Date: 2022/06/18/14:55
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户不存在");
        }
        user.setRoles(userDao.getRolesByUid(user.getId()));
        return user;
    }
}
