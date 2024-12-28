package com.example.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter.domain.User;

/**
* @author Administrator
* &#064;description  针对表【user】的数据库操作Service
* &#064;createDate  2024-12-28 08:46:47
 */
public interface UserService extends IService<User> {

    long userRegister(String userName, String password, String checkPassword) throws Exception;
    User userLogin(String userName, String password) throws Exception;
}
