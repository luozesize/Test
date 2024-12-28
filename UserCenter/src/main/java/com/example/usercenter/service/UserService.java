package com.example.usercenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Administrator
* &#064;description  针对表【user】的数据库操作Service
* &#064;createDate  2024-12-28 08:46:47
 */
public interface UserService extends IService<User> {


    Long userRegister(String userAccount, String password, String checkPassword) throws Exception;
    User userLogin(String userAccount, String password , HttpServletRequest httpServletRequest) throws Exception;
}
