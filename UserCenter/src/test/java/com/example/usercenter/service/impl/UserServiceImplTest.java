package com.example.usercenter.service.impl;

import com.example.usercenter.domain.User;
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
        String userName = "luozesix";
        String password = "123456798";

        User user = service.userLogin(userName, password);
        System.out.println(user);

    }

}