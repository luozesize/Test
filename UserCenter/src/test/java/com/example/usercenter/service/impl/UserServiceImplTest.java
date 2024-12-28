package com.example.usercenter.service.impl;

import com.example.usercenter.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService service;

    @Test
    public void test() throws Exception {
        String userAccount = "luozesi";
        String password = "123456798";
        service.userRegister(userAccount,password,password);

    }

}