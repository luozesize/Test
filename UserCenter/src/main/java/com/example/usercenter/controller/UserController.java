package com.example.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.model.request.UserLoginRequest;
import com.example.usercenter.model.request.UserRegisterRequest;
import com.example.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;


/**
 * ；用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) throws Exception {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, password, checkPassword)) {
            return null;
        }

        return userService.userRegister(userAccount, password, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) throws Exception {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();

        if (StringUtils.isAnyBlank(userAccount, password)) {
            return null;
        }

        return userService.userLogin(userAccount, password, httpServletRequest);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String userName ,HttpServletRequest request) {
        //仅管理员可以查询
        if (isAdmin(request)) return new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isAnyBlank(userName)) {
            queryWrapper.like("userName", userName);

        }
        return userService.list(queryWrapper);

    }

    @PostMapping("/delete")
    public boolean deleteUsers(@RequestBody long id, HttpServletRequest request) {
        if (isAdmin(request)) return false;
        if (id <= 0) {
            return false;
        }

        return userService.removeById(id);

    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */

    private static boolean isAdmin(HttpServletRequest request) {
        //仅管理员可以查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user == null || user.getUserRole() != ADMIN_ROLE;
    }
}
