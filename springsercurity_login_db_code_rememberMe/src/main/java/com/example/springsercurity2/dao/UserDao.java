package com.example.springsercurity2.dao;

import com.example.springsercurity2.entity.Role;
import com.example.springsercurity2.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: LaiLai
 * @Date: 2022/06/18/15:07
 */
@Mapper
public interface UserDao {
//    根据用户名称来查询出对应的用户信息
    User loadUserByUsername(String username);
    //根据⽤户id查询⻆⾊
    List<Role> getRolesByUid(Integer uid);
}
